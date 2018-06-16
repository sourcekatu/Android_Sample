package sample.sample_socket_server_repeat;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Kazuki on 2018/06/16.
 */

public class ReceiveASyncTask extends AsyncTask<Socket, Void, String> {

    private EditText mew;
    private BufferedReader reader = null;
    String receiveString = "NG";

    public ReceiveASyncTask(EditText ew)
    {
        mew = ew;
    }

    // バックグラウンドの処理。ここではTextViewの操作はできない。
    @Override
    protected String doInBackground(Socket... sockets) {
        // 受信したデータを格納
        try {
            reader = new BufferedReader(new InputStreamReader(sockets[0].getInputStream()));
            receiveString = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return receiveString;
    }

    // doInBackgroundの処理が終了した後に呼ばれる。ここではTextViewの操作はできる。
    @Override
    protected void onPostExecute(String result) {
        mew.setText(result);
    }
}
