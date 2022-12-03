package uk.ac.tees.b1515396.teezapplication.api;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.ac.tees.b1515396.teezapplication.R;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {

    // Create variable for ArrayList and context.
    private ArrayList<CourseModel> courseModelArrayList;
    private Context context;

    // Create variable for the variables


    public CourseAdapter(ArrayList<CourseModel> courseModelArrayList, Context context) {
        this.courseModelArrayList = courseModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the UI
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, int position) {
        // set data to the view
        CourseModel model = courseModelArrayList.get(position);
        holder.courseName.setText(model.getCourseName());
        holder.courseModes.setText(model.getCourseTracks());
        holder.courseBatch.setText(model.getCourseMode());
        Picasso.get().load(model.getCourseImg()).into(holder.courseImageView);

    }

    @Override
    public int getItemCount() {
        // get the size of the arrayList.
        return courseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // create variable for views.
        private TextView courseName, courseBatch, courseModes;
        private ImageView courseImageView;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            // initialize views.
            courseName = itemView.findViewById(R.id.Tv_CourseName);
            courseBatch = itemView.findViewById(R.id.Tv_Batch);
            courseModes = itemView.findViewById(R.id.Tv_Mode);
            courseImageView = itemView.findViewById(R.id.Iv_Course);
        }

    }
}
