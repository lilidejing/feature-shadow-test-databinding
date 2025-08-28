package com.kye.utils.asyncupload;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Routing
 * Desc    TODO
 * Source
 * Created by 085455 on 2022/6/4 16:32
 * Version 1.0
 */
public class AsyncUploadUtils<T extends AsyncUploadObject> {
    /**
     * 上传监听
     */
    private final UploadListener<T> listener;
    /**
     * 数据库监听
     */
    private SaveDBListener<T> dbListener;
    /**
     * 是否同步
     */
    private boolean isSync;

    /**
     * 异步上传队列
     */
    private LinkedBlockingQueue<T> uploaderQueue;

    /**
     * 重试次数
     */
    private final int RETRY_COUNT = 10;

    public AsyncUploadUtils(UploadListener<T> listener) {
        this.listener = listener;
        if (isSync) {
            // 同步用队列，一个一个的执行
            uploaderQueue = new LinkedBlockingQueue<>();
        }
    }

    public AsyncUploadUtils(UploadListener<T> listener, boolean isSync) {
        this(listener);
        this.isSync = isSync;
    }

    public AsyncUploadUtils(UploadListener<T> listener, SaveDBListener<T> dbListener, boolean isSync) {
        this(listener, isSync);
        this.dbListener = dbListener;
    }

    public void upload(T uploadObj) {
        if (isSync) {
            syncUpload(uploadObj);
        } else {
            asyncUpload(uploadObj.businessNumber, uploadObj);
        }
    }

    /**
     * 异步上传流程
     */
    private void asyncUpload(String id, T uploadObj) {
        if (listener != null) {
            if (dbListener != null) {
                // 添加数据库
                dbListener.saveDB(uploadObj);
            }
            listener.upload(uploadObj, new UploadResultListener<T>() {
                @Override
                public void uploadSuccess(String id) {
                    remove(id);
                }

                @Override
                public boolean uploadErr(T object, boolean isRetry) {
                    remove(object.businessNumber);
                    if (isRetry && object.errCount++ < RETRY_COUNT) {
                        upload(object);
                        return true;
                    } else {
                        return false;
                    }
                }

            });
        }
    }

    /**
     * 同步上传流程
     */
    private synchronized void syncUpload(T uploadObj) {
        if (listener != null && uploaderQueue != null) {
            try {
                uploaderQueue.put(uploadObj);
                if (dbListener != null) {
                    // 添加数据库
                    dbListener.saveDB(uploadObj);
                }
                if (uploaderQueue.size() == 1) {
                    syncTakeDataUpload();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 同步取数据上传
     */
    private void syncTakeDataUpload() {
        if (uploaderQueue != null && !uploaderQueue.isEmpty()) {
            listener.upload(uploaderQueue.peek(), new UploadResultListener<T>() {
                @Override
                public void uploadSuccess(String id) {
                    remove(id);
                    syncTakeDataUpload();
                }

                @Override
                public boolean uploadErr(T object, boolean isRetry) {
                    remove(object.businessNumber);
                    syncTakeDataUpload();
                    if (isRetry && object.errCount++ < RETRY_COUNT) {
                        upload(object);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }
    }

    private void remove(String id) {
        if (dbListener != null) {
            // 移除数据库
            dbListener.removeDB(id);
        }
        if (uploaderQueue != null) {
            uploaderQueue.poll();
        }
    }

    public interface UploadListener<T extends AsyncUploadObject> {
        void upload(T uploadObj, UploadResultListener<T> listener);
    }

    public interface SaveDBListener<T extends AsyncUploadObject> {
        void saveDB(T uploadObj);

        void removeDB(String id);
    }

    public interface UploadResultListener<T extends AsyncUploadObject> {
        void uploadSuccess(String id);

        /**
         * @return 是否重试
         */
        boolean uploadErr(T object, boolean isRetry);
    }
}
