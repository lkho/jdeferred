package org.jdeferred;

public interface CancellablePromise<D, F, P> extends Promise<D, F, P>
{
    /**
     * Request for cancellation
     *
     * @param interrupt request the deferred to interrupt if necessary
     * @return this
     */
    CancellablePromise<D, F, P> cancel(boolean interrupt);

    boolean isCancelled();

    /**
     * Remove all callbacks, excluding cancellation callbacks which is installed
     * through the Deferred interface. Callbacks registered after this call will not be affected.
     * @return this
     */
    CancellablePromise<D, F, P> clearCallbacks();
}
