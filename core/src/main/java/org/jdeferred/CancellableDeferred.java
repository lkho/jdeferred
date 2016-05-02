package org.jdeferred;

public interface CancellableDeferred<D, F, P> extends Deferred<D, F, P>, CancellablePromise<D, F, P>
{
    CancellablePromise<D, F, P> promise();

    /**
     * This method will register {@link CancellationCallback} so that when a CancellablePromise
     * request cancellation ({@link CancellablePromise#cancel(boolean)}), {@link CancellationCallback} will be triggered.
     * <p/>
     * You can register multiple {@link CancellationCallback} by calling the method multiple times.
     * The order of callback trigger is based on the order you call this method.
     * <p/>
     * You can decided whether to honour the request or ignore if it is not cancellable.
     * However it is better to use the normal non-cancellable {@link Deferred} if the action
     * performed will never be cancellable.
     *
     * @return this
     * @see CancellablePromise#cancel(boolean)
     */
    CancellableDeferred<D, F, P> cancelRequested(CancellationCallback cancelCallback);
}
