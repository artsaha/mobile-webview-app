package com.example.animessageapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class MyWebChromeClient extends WebChromeClient {
    private ValueCallback<Uri> mFilePathCallback;
    private final static int FILE_REQUEST_CODE = 1;

    // For Android 5.0+
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
        mFilePathCallback = new ValueCallback<Uri>() {
            @Override
            public void onReceiveValue(Uri uri) {
                filePathCallback.onReceiveValue(new Uri[]{uri});
            }
        };

        Intent intent = fileChooserParams.createIntent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), FILE_REQUEST_CODE);

        return true;
    }

    private void startActivityForResult(Intent select_picture, int fileRequestCode) {
    }

    // For Android 4.1 - 4.4
    public void openFileChooser(ValueCallback<Uri> filePathCallback) {
        mFilePathCallback = filePathCallback;

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        startActivityForResult(Intent.createChooser(intent, "Select Picture"), FILE_REQUEST_CODE);
    }

    // For Android 3.0 - 4.0
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType) {
        openFileChooser(filePathCallback);
    }

    // For Android < 3.0
    public void openFileChooser(ValueCallback<Uri> filePathCallback, String acceptType, String capture) {
        openFileChooser(filePathCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_REQUEST_CODE) {
            if (mFilePathCallback != null) {
                if (resultCode == Activity.RESULT_OK) {
                    Uri result = data.getData();
                    mFilePathCallback.onReceiveValue(result);
                } else {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = null;
            }
        }
    }
}
