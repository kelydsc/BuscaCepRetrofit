package br.com.buscacepretrofit.model;

public interface SimpleCallback<T> {

    void onResponse (T response);
    void onError (String error);
}
