package com.sortscript.serfix;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sortscript.serfix.databinding.FragmentProviderListBinding;

import java.util.ArrayList;

public class ProvidersListFragment extends Fragment implements UserListAdapter.ItemClickListener {
    FragmentProviderListBinding binding;
    ArrayList<ModelForFirebase> arrayList = new ArrayList<>();
    ArrayList<ModelForFirebase> user = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProviderListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        getProvidersFromFirebase();

        return view;
    }


    String myRole = "";

    private void getProvidersFromFirebase() {

        ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Getting Data....");
        progressDialog.show();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Serfix").child("UserDetail");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                user.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ModelForFirebase model = snapshot.getValue(ModelForFirebase.class);
                    user.add(model);

                    if (myRole.isEmpty()) {
                        if (model.getUID().equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                            myRole = model.getServiceType();
                    }
                }

                for (int i = 0; i < user.size(); i++) {
                    ModelForFirebase model = user.get(i);
                    Log.d("isAdmin", "onDataChange: " + myRole);
                    if (myRole.equals("Admin")) {
                        if (!model.getServiceType().equals("Admin"))
                            arrayList.add(model);
                    } else {
                        if (model.getServiceType().equals("Admin")) {
                            arrayList.add(model);
                        }
                    }
                }

                binding.serviceProviderRecyclerView.removeAllViews();
                binding.serviceProviderRecyclerView.setAdapter(new ProviderAdapter(arrayList, requireContext()));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    @Override
    public void request_show(ModelForFirebase model) {

    }
}
