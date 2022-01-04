package kg.geektech.taskapp37;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import kg.geektech.taskapp37.databinding.FragmentBoardBinding;
import kg.geektech.taskapp37.databinding.FragmentImageBinding;

public class ImageFragment extends Fragment {
    private FragmentImageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
binding = FragmentImageBinding.inflate(inflater, container, false);
return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("image","be fore: ");
        setImage();
        Log.e("image","after");
    }

    private void setImage() {
        String uri = requireArguments().getString("kay1");
        Glide.with(binding.windowImage).load(uri).circleCrop().into(binding.windowImage);
    }
}