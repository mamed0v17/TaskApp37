package kg.geektech.taskapp37;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
        click();
        saveLogin(prefs);
        saveImage(prefs);
        if (!prefs.getImage().equals("")){
            Glide.with(binding.gallery).load(prefs.getImage()).circleCrop().into(binding.gallery);
            change = true;
        }
    }

    private void saveImage(Prefs prefs) {
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        Glide.with(binding.gallery).load(uri).circleCrop().into(binding.gallery);
                        prefs.saveImage(uri);
                        binding.gallery.setImageURI(uri);
                        change = true;


                    }
                });
        binding.gallery.setOnClickListener(view -> {
            if(change){
                AlertDialog.Builder builder  = new AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Поменять", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mGetContent.launch("image/*");
                    }
                });
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.gallery.setImageResource(R.drawable.ic_profile);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                change = false;
            }else{
                mGetContent.launch("image/*");
            }
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

    private void click() {
        binding.gallery.setOnClickListener(view -> {
            if (change && changeTwo) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setNeutralButton("Поменять", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mGetContent.launch("image/*");
                    }
                });
                builder.setPositiveButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        binding.gallery.setImageResource(R.drawable.ic_profile);
                        changeTwo = false;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                mGetContent.launch("image/*");
                change = true;
            }
        });
    }
}

