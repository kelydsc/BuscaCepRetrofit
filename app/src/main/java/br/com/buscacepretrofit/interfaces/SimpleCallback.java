package br.com.buscacepretrofit.interfaces;

public interface SimpleCallback<T> {

    void onResponse (T response);
    void onError (String error);
}
