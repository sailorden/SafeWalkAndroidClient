 /**
   * Project 5
   * @author Tushar Sharma, sharm173, 818
   * @author Weifeng Huang, huang636, LN4
   */
package edu.purdue.sharm173;
	
	
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
	
	public class MainActivity extends Activity implements SubmitCallbackListener,
			StartOverCallbackListener {
	
		/**
		 * The ClientFragment used by the activity.
		 */
		private ClientFragment clientFragment;
	
		/**
		 * The ServerFragment used by the activity.
		 */
		private ServerFragment serverFragment;
	
		/**
		 * UI component of the ActionBar used for navigation.
		 */
		private Button left;
		private Button right;
		private TextView title;
	
		/**
		 * Called once the activity is created.
		 */
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main_layout);
	
			this.clientFragment = ClientFragment.newInstance(this);
			this.serverFragment = ServerFragment.newInstance();
	
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.fl_main, this.clientFragment);
			ft.commit();
			
		}
	
		/**
		 * Creates the ActionBar: - Inflates the layout - Extracts the components
		 */
		@SuppressLint("InflateParams")
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			final ViewGroup actionBarLayout = (ViewGroup) getLayoutInflater()
					.inflate(R.layout.action_bar, null);
	
			// Set up the ActionBar
			final ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setCustomView(actionBarLayout);
	
			// Extract the UI component.
			this.title = (TextView) actionBarLayout.findViewById(R.id.tv_title);
			this.left = (Button) actionBarLayout.findViewById(R.id.bu_left);
			this.right = (Button) actionBarLayout.findViewById(R.id.bu_right);
			this.right.setVisibility(View.INVISIBLE);
			return true;
		}
	
		/**
		 * Callback function called when the user click on the right button of the
		 * ActionBar.
		 * 
		 * @param v
		 */
		public void onRightClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
	
			this.title.setText(this.getResources().getString(R.string.client));
			this.left.setVisibility(View.VISIBLE);
			this.right.setVisibility(View.INVISIBLE);
			ft.replace(R.id.fl_main, this.clientFragment);
			ft.commit();
			
		}
	
		/**
		 * Callback function called when the user click on the left button of the
		 * ActionBar.
		 * 
		 * @param v
		 */
		public void onLeftClick(View v) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
	
			this.title.setText(this.getResources().getString(R.string.server));
			this.left.setVisibility(View.INVISIBLE);
			this.right.setVisibility(View.VISIBLE);
			ft.replace(R.id.fl_main, this.serverFragment);
			ft.commit();
	
		}
	
		/**
		 * Callback function called when the user click on the submit button.
		 */
		@Override
		public void onSubmit() {
			// TODO: Get client info via client fragment
			String name = this.clientFragment.getName();
			String from = this.clientFragment.getFrom();
			String to = this.clientFragment.getTo();
			int type = this.clientFragment.getType();
	
			// Server info
			String host = this.serverFragment.getHost(getResources().getString(
					R.string.default_host));
			int port = this.serverFragment.getPort(Integer.parseInt(getResources()
					.getString(R.string.default_port)));
			// TODO: sanity check the results of the previous two dialogs//not done
	
	
			// TODO: Need to get command from client fragment
			String command = this.clientFragment.getCommand();
			if(host.contains(" ") || host.equals("")) {
				
				new AlertDialog.Builder(this)
			    .setTitle("Error message!")
			    .setMessage("Host is invalid.")
			    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			           
			        	dialog.cancel();
			        	
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			}
			else if(!(port >= 1 && port <= 65535)) {
				new AlertDialog.Builder(this)
			    .setTitle("Error message!")
			    .setMessage("Port is invalid.")
			    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            
			        	dialog.cancel();
			        	
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				
			}
			else if(name.contains(",") || name.equals("")) {
				new AlertDialog.Builder(this)
			    .setTitle("Error message!")
			    .setMessage("Name is invalid.")
			    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        	
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
				
			}
			else if(!(type == 1 || type == 2 || type == 0)) {
				new AlertDialog.Builder(this)
			    .setTitle("Error message!")
			    .setMessage("Type is invalid.")
			    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        	
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			}
			else if(!(from.equals("CL50") || from.equals("PMU") || from.equals("PUSH") || from.equals("LWSN") || from.equals("EE"))) {
				new AlertDialog.Builder(this)
			    .setTitle("Error message!")
			    .setMessage("From is invalid.")
			    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        	
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			}
			else if(to.equals(from) || !(to.equals("CL50") || to.equals("PMU") || to.equals("PUSH") || to.equals("LWSN") || to.equals("EE") 
					)) {
				if (to.equals("*") && type == 2) {
					new AlertDialog.Builder(this)
				    .setTitle("Error message!")
				    .setMessage("To cannot be * for volunteer.")
				    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				        public void onClick(DialogInterface dialog, int which) { 
				            // continue with delete
				        	dialog.cancel();
				        	
				        }
				     }).setIcon(android.R.drawable.ic_dialog_alert)
				     .show();
				}
				
			else {
				new AlertDialog.Builder(this)
			    .setTitle("Error message!")
			    .setMessage("To is invalid.")
			    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			            // continue with delete
			        	dialog.cancel();
			        	
			        }
			     }).setIcon(android.R.drawable.ic_dialog_alert)
			     .show();
			}
			}
			
			
	else {//Valid 
			this.clientFragment.reset();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
	
			this.title.setText(getResources().getString(R.string.match));
			this.left.setVisibility(View.INVISIBLE);
			this.right.setVisibility(View.INVISIBLE);
	
			// TODO: You may want additional parameters here if you tailor
			// the match fragment
			MatchFragment frag = MatchFragment.newInstance(this, host, port,
					command);
	
			ft.replace(R.id.fl_main, frag);
			ft.commit();
	}
			//
		}
	
		/**
		 * Callback function call from MatchFragment when the user want to create a
		 * new request.
		 */
		@Override
		public void onStartOver() {
			
			onRightClick(null);
			
			
			
			
		}
	
	}
