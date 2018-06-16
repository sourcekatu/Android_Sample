package sample.sample_socket_server_repeat;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

// AsyncTaskの引数は以下の用途で使用。
// 第一引数：doInBackgroundの引数
// 第二引数：onProgressUpdateの引数
// 第三引数：doInBackgroundの戻り値 and onPostExecuteの引数
public class ASyncTaskTest extends AsyncTask<Void, Void, String> {

    private String mstring;
    private EditText mew;
    ReceiveASyncTask ak2;


    public ASyncTaskTest(EditText ew)
    {
        mew = ew;
    }

    // バックグラウンドの処理。ここではTextViewの操作はできない。
    @Override
    protected String doInBackground(Void... voids) {
        // データ受信準備
        ServerSocket serverSocket = null;
        Socket socket;

        // データ受信
        try {
            serverSocket = new ServerSocket(5000);

            while (true) {
                // ここでデータを受信するまで待機
                socket = serverSocket.accept();

                ak2 = new ReceiveASyncTask(mew);

                //ak2.execute(socket);  // 非同期タスクを実行
                ak2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // doInBackgroundの処理が終了した後に呼ばれる。ここではTextViewの操作はできる。
    @Override
    protected void onPostExecute(String result) {
    }
}
