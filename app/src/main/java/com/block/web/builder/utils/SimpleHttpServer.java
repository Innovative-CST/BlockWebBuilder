package com.block.web.builder.utils;

import fi.iki.elonen.NanoHTTPD;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import android.util.Log;

/*
 * Original Author - raredeveloper
 * Source - https://github.com/Visual-Code-Space/Visual-Code-Space/blob/main/compiler/src/main/java/com/raredev/vcspace/compiler/html/SimpleHttpServer.java
 */

public class SimpleHttpServer {
  public WebServer server;

  private int port;

  private String NameFolder;
  private String indexFile;

  public SimpleHttpServer(int port, String NameFolder, String indexFile) {
    this.port = port;
    this.NameFolder = NameFolder;
    this.indexFile = indexFile;
  }

  public void startServer() {
    try {
      server = new WebServer(port);
      server.start();
    } catch (IOException e) {
    }
  }

  public void stopServer() {
    if (server != null) {
      server.stop();
    }
  }

  public String getLocalIpAddress() {
    return "http://localhost:" + port;
  }

  private class WebServer extends NanoHTTPD {

    public WebServer(int port) {
      super("localhost", port);
    }

    @Override
    public Response serve(IHTTPSession session) {
      String uri = session.getUri();
      if (uri.endsWith("/")) {
        uri += indexFile;
      }
      String filePath = NameFolder + uri;

      try {
        if (new File(filePath).exists()) {
          FileInputStream fis = new FileInputStream(filePath);

          int contentLength = fis.available();

          return newFixedLengthResponse(
              NanoHTTPD.Response.Status.OK, getMimeTypeForFile(filePath), fis, contentLength);
        } else {
          return newFixedLengthResponse(
              NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "File not found.");
        }
      } catch (IOException e) {
        Log.e("WebServer", e.toString());
        e.printStackTrace();
        return newFixedLengthResponse(
            NanoHTTPD.Response.Status.INTERNAL_ERROR,
            NanoHTTPD.MIME_PLAINTEXT,
            "Internal server error.");
      }
    }
  }
}
