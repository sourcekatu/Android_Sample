package sample.sample_datagram_socket_server_repeat;

import android.os.AsyncTask;
import android.widget.EditText;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

// AsyncTaskの引数は以下の用途で使用。
// 第一引数：doInBackgroundの引数
// 第二引数：onProgressUpdateの引数
// 第三引数：doInBackgroundの戻り値 and onPostExecuteの引数
public class ASyncTaskTest extends AsyncTask<Void, Void, String> implements ReceiveASyncTask.CallBackTask{

    private String mstring;
    private EditText mew;
    ReceiveASyncTask ak2;
    boolean misOk = true;


    public ASyncTaskTest(EditText ew)
    {
        mew = ew;
    }

    // バックグラウンドの処理。ここではTextViewの操作はできない。
    @Override
    protected String doInBackground(Void... voids) {
        // データ受信準備
        byte receiveBuff[] = new byte[1000];
        DatagramSocket datagramSocket = null;
        DatagramPacket datagramPacket = new DatagramPacket(receiveBuff, receiveBuff.length);

        // データ受信
        try {
            datagramSocket = new DatagramSocket(5000);

            while (true) {
                if (misOk) {
                    // ここでデータを受信するまで待機
                    datagramSocket.receive(datagramPacket);

                    misOk = false;
                    ak2 = new ReceiveASyncTask(mew);
                    ak2.setOnCallBack(this);
                    //ak2.execute(datagramSocket);  // 非同期タスクを実行
                    ak2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, datagramPacket);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            datagramSocket.close();
        }

        return null;
    }

    // doInBackgroundの処理が終了した後に呼ばれる。ここではTextViewの操作はできる。
    @Override
    protected void onPostExecute(String result) {
    }

    @Override
    public void CallBack() {
        ak2 = null;
        misOk = true;
    }
}
