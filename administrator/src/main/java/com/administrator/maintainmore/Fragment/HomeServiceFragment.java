package com.administrator.maintainmore.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.administrator.maintainmore.Adapters.HomeServiceCardAdapter;
import com.administrator.maintainmore.Models.HomeServiceCardModal;
import com.administrator.maintainmore.R;
import com.administrator.maintainmore.UpdateServiceActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class HomeServiceFragment extends Fragment
        implements HomeServiceCardAdapter.viewHolder.OnHomeServiceCardClickListener{

    private static final String TAG = "HomeServiceFragmentInfo";


    FirebaseFirestore db;

    public HomeServiceFragment() {
        // Required empty public constructor
    }
    RecyclerView recyclerView_homeServices;

    ArrayList<HomeServiceCardModal> homeServiceCardModals = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_service, container, false);

        db = FirebaseFirestore.getInstance();

        recyclerView_homeServices = view.findViewById(R.id.recycleView_homeServices);


        db.collection("Home Services").addSnapshotListener((value, error) -> {
            homeServiceCardModals.clear();
            assert value != null;
            for (DocumentSnapshot snapshot: value){
                homeServiceCardModals.add(new HomeServiceCardModal(snapshot.getId(),
                                snapshot.getString("serviceName"),
                                snapshot.getString("serviceDescription"),
                                snapshot.getString("requiredTime"),
                                snapshot.getString("servicePrice"),
                                snapshot.getString("iconUrl"),
                                snapshot.getString("backgroundImageUrl")
                        )

                );
            }
            HomeServiceCardAdapter serviceCardAdapter = new HomeServiceCardAdapter(homeServiceCardModals, getContext(), this);
            recyclerView_homeServices.setAdapter(serviceCardAdapter);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView_homeServices.setLayoutManager(linearLayoutManager);

        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView_homeServices);

        return view;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            String service = homeServiceCardModals.get(position).getServiceID();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    homeServiceCardModals.remove(position);
                    db.collection("Personal Services").document(service).delete()
                            .addOnSuccessListener(unused ->
                                    Toast.makeText(getActivity(), "ServiceDeleted", Toast.LENGTH_SHORT).show()
                            );
                    break;
                case  ItemTouchHelper.RIGHT:

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.color_danger))
                    .addActionIcon(R.drawable.ic_delete)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


    @SuppressLint("LongLogTag")
    @Override
    public void onHomeServiceCardClick(int position) {

        String ID = homeServiceCardModals.get(position).getServiceID();
        String name = homeServiceCardModals.get(position).getServiceName();

        Log.i(TAG,"ID: " + ID);


        Intent intent = new Intent(getActivity(), UpdateServiceActivity.class);

        intent.putExtra("serviceID", ID);
        intent.putExtra("serviceName", name);

        startActivity(intent);
    }
}