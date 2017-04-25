package com.example.kelfor.socketcom;


//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.PrintWriter;
//import java.net.Socket;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//public class MainActivity extends Activity implements Runnable {
//    private TextView tv_msg = null;
//    private EditText ed_msg = null;
//    private Button btn_send = null;
//    // private Button btn_login = null;
//    private static final String HOST = "10.0.2.2";
//    private static final int PORT = 9999;
//    private Socket socket = null;
//    private BufferedReader in = null;
//    private PrintWriter out = null;
//    private String content = "";
//接收线程发送过来信息，并用TextView显示
//    public Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            tv_msg.setText(content);
//        }
//    };

//   @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        tv_msg = (TextView) findViewById(R.id.textView);
//        ed_msg = (EditText) findViewById(R.id.edit);
//        btn_send = (Button) findViewById(R.id.btnSend);
//
//        try {
//            socket = new Socket(HOST, PORT);
//            in = new BufferedReader(new InputStreamReader(socket
//                    .getInputStream()));
//            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
//                    socket.getOutputStream())), true);
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            ShowDialog("login exception" + ex.getMessage());
//        }
//        btn_send.setOnClickListener(new Button.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                String msg = ed_msg.getText().toString();
//                if (socket.isConnected()) {
//                    if (!socket.isOutputShutdown()) {
//                        out.println(msg);
//                    }
//                }
//            }
//        });
//        //启动线程，接收服务器发送过来的数据
//        new Thread(MainActivity.this).start();
//    }
//    /**
//     * 如果连接出现异常，弹出AlertDialog！
//     */
//    public void ShowDialog(String msg) {
//        new AlertDialog.Builder(this).setTitle("notification").setMessage(msg)
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).show();
//    }
//    /**
//     * 读取服务器发来的信息，并通过Handler发给UI线程
//     */
//    public void run() {
//        try {
//            while (true) {
//                if (!socket.isClosed()) {
//                    if (socket.isConnected()) {
//                        if (!socket.isInputShutdown()) {
//                            if ((content = in.readLine()) != null) {
//                                content += "\n";
//                                mHandler.sendMessage(mHandler.obtainMessage());
//                            } else {
//
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class MainActivity extends Activity implements Runnable {

    EditText show;
    private PrintWriter out;
    BufferedReader br;
    Button btnSend;
    Socket socket=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show = (EditText)findViewById(R.id.edit);
        show.setText("来自服务器的数据：");
        btnSend = (Button)findViewById(R.id.btnSend);



       // } catch (IOException e) {
       //     e.printStackTrace();
      //  }
//
//        new Thread()
//        {
//            @Override
//            public void run()
//            {
//
//            }
//        }.start();
        new Thread(MainActivity.this).start();

        btnSend.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  switch (v.getId())
                {
                    //case R.id.btnSend:
                    // show.setText("SendOK");

                    String msg = show.getText().toString();
                    out.print(msg);
                    out.flush();//Very Important!
                    //  break;
                    // default:
                    //     break;
                }
            }
        });

    }

    @Override
    public void run() {

        try {
            //建立连接到远程服务器的Socket
            socket = new Socket("100.64.148.130",9527);

            out = new PrintWriter( socket.getOutputStream());
            //将Socket对应的输入流包装成BufferedReader

             br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             
            try {
                while (true) {
                    if (!socket.isClosed()) {
                        // show.setText("sdfsdfdsfsdfsdfdsfsdfdsfdsNOCLOSED");

                        if (socket.isConnected()) {

                          ////  i = i+1;
                       //      show.setText(String.format("Connected" + i ));

                            String str = br.readLine();
                            if(str!=null)
                            {
                                show.setText("HH");

                            }

                            //进行普通的I/O操作

//                                    if(br != null)
//                                        show.setText("Read OK");
//                                    i = br.read();
//                                    show.setText(String.format("Connected" + i ));
//                                    String line = br.readLine();
//
//
//
//
//                                    show.setText(String.format("Connected" + i ));
//                            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
//                                    socket.getOutputStream())), true);
//                            String msg = show.getText().toString();
//                            out.print(msg);
//                            //out.flush();
//                            //show.setText("None"+line);
//                            // br.close();
//                            i = i+1;
//
//                            out.close();
//                            //sleep(10);
                        }
                        else {
                            show.setText("Disconnected");

                            //sleep(2000);
                        }

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }




            socket.close();
        } catch (Exception e) {
            show.setText("Connect Failed");
            e.printStackTrace();
        }
    }

//
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R.menu.main, menu);
//            return true;
//        }

//        @Override
//        public boolean onOptionsItemSelected(MenuItem item) {
//            // Handle action bar item clicks here. The action bar will
//            // automatically handle clicks on the Home/Up button, so long
//            // as you specify a parent activity in AndroidManifest.xml.
//            int id = item.getItemId();
//            if (id == R.id.action_settings) {
//                return true;
//            }
//            return super.onOptionsItemSelected(item);
//        }
}