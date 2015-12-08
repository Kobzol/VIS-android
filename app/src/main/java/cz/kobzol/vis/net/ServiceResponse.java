package cz.kobzol.vis.net;

public interface ServiceResponse<T>
{
    void onResult(T result);
    void onError(Throwable exception);
}
