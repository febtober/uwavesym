package org.febtober.uwavesym;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class ParametersFragment extends DialogFragment {

    private EditText et_frequency;
    private EditText et_substrateHeight;
    private EditText et_substratePermittivity;
    private Button button_update;
    private Button button_cancel;

    private double frequency;
    private double substrateHeight;
    private double substratePermittivity;

//    private OnFragmentInteractionListener mListener;

    public static ParametersFragment newInstance() {
        ParametersFragment fragment = new ParametersFragment();
        return fragment;
    }

    public ParametersFragment() {
        // Empty constructor required for Fragment
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parameters, container, false);
        et_frequency = (EditText) view.findViewById(R.id.et_frequency);
        et_substrateHeight = (EditText) view.findViewById(R.id.et_substrate_height);
        et_substratePermittivity = (EditText) view.findViewById(R.id.et_substrate_permittivity);
        button_update = (Button) view.findViewById(R.id.button_fragment_update);
        button_cancel = (Button) view.findViewById(R.id.button_fragment_cancel);
        getDialog().setTitle(R.string.circuit_parms);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                frequency = Double.valueOf(et_frequency.getText().toString());
                substrateHeight = Double.valueOf(et_substrateHeight.getText().toString());
                substratePermittivity = Double.valueOf(et_substratePermittivity.getText().toString());
                ((WorkspaceActivity) getActivity()).onFragmentUpdate();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((WorkspaceActivity) getActivity()).onFragmentCancel();
            }
        });

        return view;
    }

    public double[] getParameters() {
        return new double[] {frequency, substrateHeight, substratePermittivity};
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
