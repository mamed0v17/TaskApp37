package kg.geektech.taskapp37.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kg.geektech.taskapp37.databinding.ItemNewsBinding;
import kg.geektech.taskapp37.interfaces.OnItemClickListener;
import kg.geektech.taskapp37.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> list;
    private OnItemClickListener onItemClickListener;
    private ItemNewsBinding binding;

    public NewsAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position), onItemClickListener);
        if (position % 2 == 0) {
            binding.textTitle.setBackgroundColor(Color.GREEN);
        } else {
            binding.textTitle.setBackgroundColor(Color.WHITE);
        }
        holder.bind(list.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(News news) {
        list.add(0, news);
        notifyItemInserted(0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public News getItem(int pos) {
        return list.get(pos);
    }

    public void removeItem(News news, int pos) {
        this.list.remove(news);
        notifyItemRemoved(pos);
    }

    public void updateItem(News news) {
        int index = list.indexOf(news);
        list.set(index, news);
        notifyItemChanged(index);
    }

    public void addItems(List<News> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;

        public ViewHolder(ItemNewsBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });

        }

        public void bind(News news, OnItemClickListener onItemClickListener) {
            String time = (String) android.text.format.DateFormat.format("HH:mm dd MMM yyyy", new Date(news.getCreateAt()));
            binding.textTitle.setText(news.getTitle());
            binding.timeTitle.setText(time);
        }
    }
}
