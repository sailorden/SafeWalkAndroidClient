package edu.purdue.sharm173;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * This fragment is the "page" where the user inputs information about the
 * request, he/she wishes to send.
 *
 * @author YL
 */
public class ClientFragment extends Fragment implements OnClickListener {

	
	/**
	 * Activity which have to receive callbacks.
	 */
	private SubmitCallbackListener activity;
	private EditText name;
	private Spinner from;
	private Spinner to;
	private RadioGroup type;
	/**
	 * Creates a ProfileFragment
	 * 
	 * @param activity
	 *            activity to notify once the user click on the submit Button.
	 * 
	 *            ** DO NOT CREATE A CONSTRUCTOR FOR MatchFragment **
	 * 
	 * @return the fragment initialized.
	 */
	// ** DO NOT CREATE A CONSTRUCTOR FOR ProfileFragment **
	public static ClientFragment newInstance(SubmitCallbackListener activity) {
		
		ClientFragment f = new ClientFragment();

		f.activity = activity;
		return f;
	}

	/**
	 * Called when the fragment will be displayed.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//
		
		
		if (container == null) {
			return null;
		}

		View view = inflater.inflate(R.layout.client_fragment_layout,
				container, false);

		/**
		 * Register this fragment to be the OnClickListener for the submit
		 * Button.
		 */
		view.findViewById(R.id.bu_submit).setOnClickListener(this);
		

		// TODO: import your Views from the layout here. See example in
		// ServerFragment.
		this.name = (EditText) view.findViewById(R.id.editText1);
		this.from = (Spinner) view.findViewById(R.id.spinner1);
		this.to = (Spinner) view.findViewById(R.id.spinner2);
		this.type = (RadioGroup) view.findViewById(R.id.radioGroup1);

		return view;
	}

	/**
	 * Callback function for the OnClickListener interface.
	 */
	@Override
	public void onClick(View v) {
		this.activity.onSubmit();
		
	}
	public String getName() {
		return name.getText().toString();
	}
	//
	public String getFrom() {
		return from.getSelectedItem().toString();
	}
	public String getTo() {
		return to.getSelectedItem().toString();
	}
	//
	public int getType() {
		int radioButtonID = type.getCheckedRadioButtonId();
		View radioButton = type.findViewById(radioButtonID);
		int idx = type.indexOfChild(radioButton);
		return idx;
	}
	public String getCommand() {
		String c1 = name.getText().toString();
		String c2 = from.getSelectedItem().toString();
		String c3 = to.getSelectedItem().toString();
		int radioButtonID = type.getCheckedRadioButtonId();
		View radioButton = type.findViewById(radioButtonID);
		int idx = type.indexOfChild(radioButton);
		String c4 = String.valueOf(idx);
		String command = c1 + "," + c2 + "," + c3 + "," + c4;
		return command;
	}
	public void reset() {
		name.setText("");
		from.setSelection(0);
		to.setSelection(0);
		type.clearCheck();
	}
	
}
