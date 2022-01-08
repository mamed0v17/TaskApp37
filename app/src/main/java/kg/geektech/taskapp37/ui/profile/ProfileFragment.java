package kg.geektech.taskapp37.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kg.geektech.taskapp37.Prefs;
import kg.geektech.taskapp37.R;
import kg.geektech.taskapp37.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private boolean change = false;
    private boolean changeTwo = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Prefs prefs = new Prefs(requireContext());
        click(prefs);
        exitGoogle();
        saveLogin(prefs);
        if (!prefs.getImage().equals("")) {
            Glide.with(binding.gallery).load(prefs.getImage()).circleCrop().into(binding.gallery);
            change = true;
        }

    }

    private void exitGoogle() {
        binding.btnExit.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setNeutralButton("Отмена",((dialog, which) -> {}));
            builder.setNegativeButton("Выйти",((dialog, which) -> {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.loginFragment);
            }));
            @SuppressLint("InflateParams") ConstraintLayout constraintLayout  = (ConstraintLayout) getLayoutInflater().inflate(R.layout.item_alert_dialog,null);
            builder.setView(constraintLayout);
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    private void saveLogin(Prefs prefs) {
        binding.editTextLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                prefs.saveLogin(editable.toString());
            }
        });
        binding.editTextLogin.setText(prefs.getLogin());

    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override

                public void onActivityResult(Uri uri) {
                    if (uri == null) {
                        change = false;
                    } else {


                        binding.gallery.setImageURI(uri);
                        changeTwo = true;
                    }
                }
            });

    private void click(Prefs prefs) {
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Glide.with(binding.gallery).load(uri).circleCrop().into(binding.gallery);
                        prefs.saveImage(uri);
                        change = true;
                    }
                });
        binding.gallery.setOnClickListener(view -> {
            if (!prefs.getImage().equals("")) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                Bundle bundle = new Bundle();
                bundle.putString("kay1", prefs.getImage());
                navController.navigate(R.id.imageFragment, bundle);
            } else
                Toast.makeText(requireContext(), "Фото нету ", Toast.LENGTH_SHORT).show();
        });

        binding.btnRefactor.setOnClickListener(view -> {
            if (change) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Заменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mGetContent.launch("image/*");
                    }
                });
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        binding.gallery.setImageResource(R.drawable.ic_profile);
                        prefs.deleteUserImage();

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                change = false;
            } else {
                mGetContent.launch("image/*");
                change = true;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}

