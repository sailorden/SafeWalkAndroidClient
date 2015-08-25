package edu.purdue.sharm173;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import android.app.Fragment;
import android.util.Log;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This fragment is the "page" where the application display the log from the
 * server and wait for a match.
 *
 * @author YL
 */
public class MatchFragment extends Fragment implements OnClickListener {

	private static final String DEBUG_TAG = "DEBUG";

	/**
	 * Activity which have to receive callbacks.
	 */
	private StartOverCallbackListener activity;

	/**
	 * AsyncTask sending the request to the server.
	 */
	private Client client;

	/**
	 * Coordinate of the server.
	 */
	

	/**
	 * Command the user should send.
	 */
	private String command;

	// TODO: your own class fields here//done
	private String host;
	private int port;
	private TextView partner;
	private TextView from;
	private TextView to;
	private TextView log;
	private TextView message;

	// Class methods
	/**
	 * Creates a MatchFragment
	 * 
	 * @param activity
	 *            activity to notify once the user click on the start over
	 *            Button.
	 * @param host
	 *            address or IP address of the server.
	 * @param port
	 *            port number.
	 * 
	 * @param command
	 *            command you have to send to the server.
	 * 
	 * @return the fragment initialized.
	 */
	// TODO: you can add more parameters, follow the way we did it.
	// ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
	public static MatchFragment newInstance(StartOverCallbackListener activity,
			String host, int port, String command) {
		MatchFragment f = new MatchFragment();

		f.activity = activity;
		f.host = host;
		f.port = port;
		f.command = command;

		return f;
	}

	/**
	 * Called when the fragment will be displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (container == null) {
			return null;
		}

		View view = inflater.inflate(R.layout.match_fragment_layout, container,
				false);

		/**
		 * Register this fragment to be the OnClickListener for the startover
		 * button.
		 */
		view.findViewById(R.id.bu_start_over).setOnClickListener(this);

		// TODO: import your Views from the layout here. See example in
		// ServerFragment.
		this.partner = (TextView) view.findViewById(R.id.partner_text);
		this.from = (TextView) view.findViewById(R.id.from_text);
		this.to = (TextView) view.findViewById(R.id.to_text);
		this.log = (TextView) view.findViewById(R.id.log);
		this.message = (TextView) view.findViewById(R.id.message);

		/**
		 * Launch the AsyncTask
		 */
		this.client = new Client();
		this.client.execute("");

		return view;
	}

	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		/**
		 * Close the AsyncTask if still running.
		 */
		this.client.close();

		/**
		 * Notify the Activity.
		 */
		this.activity.onStartOver();
	}

	class Client extends AsyncTask<String, String, String> implements Closeable {

		/**
		 * NOTE: you can access MatchFragment field from this class:
		 * 
		 * Example: The statement in doInBackground will print the message in
		 * the Eclipse LogCat view.
		 */

		/**
		 * The system calls this to perform work in a worker thread and delivers
		 * it the parameters given to AsyncTask.execute()
		 */
		protected String doInBackground(String... params) {

			/**
			 * TODO: Your Client code here.
			 */
			String response = "";
			
			try {
				 // Instantiate a Date object
			      // Date date = new Date();
			        
			       // display formatted date
			      // System.out.printf("%s %tB %<te, %<tY", 
			     //                    "Due date:", date);
				String timeStamp = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
				publishProgress(timeStamp,"Connecting to the server.");
				Socket client = new Socket(host,port);
				if(client.isConnected()) {
					String timeStamp1 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
					publishProgress(timeStamp1, "Connection to the server. Success.");
				}
				else {
					String timeStamp2 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
					publishProgress(timeStamp2,"The server is not available.");
				}
				BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                writer.println(command);
                //
                
                String loga1 = command.split(",")[0];
                String loga;
                if(command.split(",")[3].equals("0")) {
                loga = ", with no preference of being a requester or volunteer sent a request to move from ";
                }
                else if(command.split(",")[3].equals("1")) {
                	loga = ", with preference of being a requester sent a request to move from ";
                	
                }
                else if(command.split(",")[3].equals("2")) {
                	loga = ", with preference of being a volunteer sent a request to move from ";
                }
                else {
                	loga = "";
                }
                String log2 = loga1 + loga + command.split(",")[1] +" to " + command.split(",")[2];
               //
                String timeStamp3 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
                publishProgress(timeStamp3,log2);
                response = reader.readLine();
                if(response != null && !response.equals("ERROR: connection reset") && response.contains("RESPONSE:")) {
                	String timeStamp4 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
                	publishProgress(timeStamp4,"A pair has been found by the server.");
                	writer.println("ACK:");
                	client.close();
                }
                else if(response.equals("ERROR: connection reset")) {
                	publishProgress("The connection has been reset by the server");
                }
				
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				String timeStamp5 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
				publishProgress(timeStamp5, "Error! Unknown host.");
				Log.d(DEBUG_TAG, " The server is not available.");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				String timeStamp6 = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(Calendar.getInstance().getTime());
				publishProgress(timeStamp6, "Error! IO exception.");
				Log.d(DEBUG_TAG, " The server is not available.");
				e.printStackTrace();
			}
			Log.d(DEBUG_TAG, String
					.format("The Server at the address %s uses the port %d",
							host, port));

			Log.d(DEBUG_TAG, String.format(
					"The Client will send the command: %s", command));

			return response;
		}
		public void close() {
                    // TODO: Clean up the client
			log.setText("");
			message.setText("");
			partner.setText("");
			from.setText("");
			to.setText("");
		}

		/**
		 * The system calls this to perform work in the UI thread and delivers
		 * the result from doInBackground()
		 */

		// TODO: use the following method to update the UI.
		// ** DO NOT TRY TO CALL UI METHODS FROM doInBackground!!!!!!!!!! **

		/**
		 * Method executed just before the task.
		 */
		@Override
		protected void onPreExecute() {
			
		}

		/**
		 * Method executed once the task is completed.
		 */
		@Override
		protected void onPostExecute(String result) {
			if(result.equals("")) {
				message.setText("Connection to the server was unsuccessful.");
			}
			else  if(result.contains("RESPONSE:")){
			partner.setText(result.split(",")[0].substring(10));
			from.setText(result.split(",")[1]);
			to.setText(result.split(",")[2]);
			message.setText("Congratulations! A match has been found.");
			}
			else if(result.equals("ERROR: connection reset")) {
				message.setText("Error! The connection has been reset by the server.");
			}
			}

		/**
		 * Method executed when progressUpdate is called in the doInBackground
		 * function.
		 */
		@Override
		protected void onProgressUpdate(String... result) {
			for(int i = 0; i < result.length; i++) {
				log.append(result[i] + " ");
			}
			log.append("\n\n\n\n");
			
		}
	}

}