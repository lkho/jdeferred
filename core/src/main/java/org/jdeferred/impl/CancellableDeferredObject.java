package org.jdeferred.impl;

import org.jdeferred.AlwaysCallback;
import org.jdeferred.DoneCallback;
import org.jdeferred.FailCallback;
import org.jdeferred.ProgressCallback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CancellableDeferredObject<D, F, P> extends DeferredObject<D, F, P>
        implements CancellableDeferred<D, F, P>
{
    protected final List<CancellationCallback> cancelCallbacks = new CopyOnWriteArrayList<CancellationCallback>();
    protected volatile boolean cancelled;
    protected volatile boolean interrupt;

    protected void triggerCancelRequested(boolean interrupt)
    {
        for (CancellationCallback callback : cancelCallbacks) {
            try {
                triggerCancelRequested(callback, interrupt);
            } catch (Exception e) {
                log.error("an uncaught exception occurred in a CancellationCallback", e);
            }
        }
        cancelCallbacks.clear();
    }

    protected void triggerCancelRequested(CancellationCallback callback, boolean interrupt)
    {
        callback.onCancellationRequested(interrupt);
    }

    @Override
    public CancellablePromise<D, F, P> cancel(boolean interrupt)
    {
        synchronized (this) {
            this.cancelled = true;
            this.interrupt = interrupt;
            triggerCancelRequested(interrupt);
        }
        return this;
    }

    @Override
    public CancellableDeferred<D, F, P> cancelRequested(CancellationCallback callback)
    {
        synchronized (this) {
            if (isCancelled()) {
                triggerCancelRequested(callback, this.interrupt);
            } else {
                cancelCallbacks.add(callback);
            }
        }
        return this;
    }

    @Override
    public CancellablePromise<D, F, P> clearCallbacks()
    {
        synchronized (this) {
            doneCallbacks.clear();
            failCallbacks.clear();
            progressCallbacks.clear();
            alwaysCallbacks.clear();
        }
        return this;
    }

    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    @Override
    public CancellablePromise<D, F, P> promise()
    {
        return this;
    }

    @Override
    public CancellablePromise<D, F, P> always(AlwaysCallback<D, F> callback)
    {
        return (CancellablePromise<D, F, P>) super.always(callback);
    }

    @Override
    public CancellablePromise<D, F, P> done(DoneCallback<D> callback)
    {
        return (CancellablePromise<D, F, P>) super.done(callback);
    }

    @Override
    public CancellablePromise<D, F, P> fail(FailCallback<F> callback)
    {
        return (CancellablePromise<D, F, P>) super.fail(callback);
    }

    @Override
    public CancellablePromise<D, F, P> progress(ProgressCallback<P> callback)
    {
        return (CancellablePromise<D, F, P>) super.progress(callback);
    }

    @Override
    public CancellablePromise<D, F, P> then(DoneCallback<D> callback)
    {
        return (CancellablePromise<D, F, P>) super.then(callback);
    }

    @Override
    public CancellablePromise<D, F, P> then(DoneCallback<D> doneCallback, FailCallback<F> failCallback)
    {
        return (CancellablePromise<D, F, P>) super.then(doneCallback, failCallback);
    }

    @Override
    public CancellablePromise<D, F, P> then(DoneCallback<D> doneCallback, FailCallback<F> failCallback, ProgressCallback<P> progressCallback)
    {
        return (CancellablePromise<D, F, P>) super.then(doneCallback, failCallback, progressCallback);
    }
}
