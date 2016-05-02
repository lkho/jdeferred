package org.jdeferred;

/**
 * @see CancellableDeferred#cancelRequested(CancellationCallback)
 * @see CancellablePromise#cancel(boolean)
 */
public interface CancellationCallback
{
    /**
     * Called when cancellation is requested by promise. Deferred can decided
     * whether to honour the request or ignore if it is not cancellable.
     * However it is better to use the normal non-cancellable {@link Deferred} by
     * confirming to the contract.
     *
     * @param interrupt whether to interrupt forcefully
     */
    void onCancellationRequested(boolean interrupt);
}
