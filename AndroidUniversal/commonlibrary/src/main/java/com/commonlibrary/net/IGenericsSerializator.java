package com.commonlibrary.net;


public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
