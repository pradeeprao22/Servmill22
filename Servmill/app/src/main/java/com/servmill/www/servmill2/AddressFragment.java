package com.servmill.www.servmill2;


/**
  A simple {@link Fragment} subclass.

public class AddressFragment extends Fragment {
View view;
TextView Address;
 EditText Country,Street,Pin,Area,plotno;
    Button save;
    public AddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address, container, false);
        Address = (TextView) view.findViewById(R.id.address);
        Country = (EditText) view.findViewById(R.id.country);
        Area= (EditText) view.findViewById(R.id.Area);
        plotno = (EditText) view.findViewById(R.id.plot_no);
        Street = (EditText) view.findViewById(R.id.street);
        Pin = (EditText) view.findViewById(R.id.pin);
        save = (Button) view.findViewById(R.id.addressButton);

        return view;
    }
}
*/