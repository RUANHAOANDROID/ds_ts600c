package com.reader.api;

import java.io.InputStream;
import java.io.OutputStream;

public interface IDevice {
    public InputStream getInput();

    public OutputStream getOutput();

    public boolean open() throws Exception;

    public void close() throws Exception;

    public void flush();

    public boolean isOpen();
}
