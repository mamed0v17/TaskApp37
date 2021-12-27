package kg.geektech.taskapp37;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kg.geektech.taskapp37.databinding.FragmentNewsBinding;
import kg.geektech.taskapp37.models.News;

public class NewsFragment extends Fragment {

    private FragmentNewsBinding binding;
    private News news;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        news = (News) requireArguments().getSerializable("news");
        if (news != null) {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Edit");
            binding.editText.setText(news.getTitle());
        } else {
            ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Add");
        }
        binding.btnSave.setOnClickListener(v -> {
            save();
        });
    }

    private void save() {
        String text = binding.editText.getText().toString().trim();
        Bundle bundle = new Bundle();
        if (news == null) {
            news = new News(text, System.currentTimeMillis());
            bundle.putSerializable("news", news);
            getParentFragmentManager().setFragmentResult("rk_news", bundle);
            App.getInstance().getDatabase().newsDao().insert(news);
        } else {
            news.setTitle(text);
            bundle.putSerializable("news", news);
            getParentFragmentManager().setFragmentResult("rk_news_update", bundle);
        }

        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}