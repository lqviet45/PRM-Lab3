package com.example.lab3;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvTraiCay;
    ArrayList<TraiCay> arrayTraiCay;
    TraiCayAdapter adapter;
    Button btnAddFruit;
    ImageView selectedImageView;
    Uri selectedImageUri;
    int selectedFruitPosition;
    Button btnMoveToActivity2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity2);
        AnhXa();

        this.adapter = new TraiCayAdapter(this, R.layout.layout, arrayTraiCay);
        lvTraiCay.setAdapter(adapter);
        btnAddFruit.setOnClickListener(v -> AddFruitAsk());
        btnMoveToActivity2.setOnClickListener(v -> MoveToActivity2());
        lvTraiCay.setOnItemClickListener((parent, view, position, id) -> showFruitDialog(position));
    }

    private void MoveToActivity2() {
        Intent intent = new Intent(MainActivity.this, Activity2.class);
        startActivity(intent);
    }

    private void AnhXa() {
        lvTraiCay = (ListView) findViewById(R.id.listViewTraiCay);
        btnAddFruit = (Button) findViewById(R.id.btnAddFruit);
        btnMoveToActivity2 = (Button) findViewById(R.id.btnMoveToActivity2);
        TraiCay traiCay1 = new TraiCay("Dâu", "Dâu tây", Uri.parse("https://th.bing.com/th/id/OIP.CmbO_IgvScBpt9QSGcMG8QHaE7?rs=1&pid=ImgDetMain"));
        TraiCay traiCay2 = new TraiCay("Chuoi", "Chuoi xu", Uri.parse("https://th.bing.com/th/id/OIP.FZ0pu7WcurIoPGU9JX4ErQHaGu?rs=1&pid=ImgDetMain"));
        arrayTraiCay = new ArrayList<>();
        arrayTraiCay.add(traiCay1);
        arrayTraiCay.add(traiCay2);
    }

    private void AddFruitAsk() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_fruit_ask, null);
        builder.setView(dialogView);

        final EditText addName = dialogView.findViewById(R.id.addTen);
        final EditText addDescription = dialogView.findViewById(R.id.addMota);
        final Button btnAddImage = dialogView.findViewById(R.id.btnSelectImage);
        selectedImageView = dialogView.findViewById(R.id.selectedImageView);

        btnAddImage.setOnClickListener(v -> {
            openImagePicker();
        });

        builder.setTitle("Thêm trái cây mới")
                .setPositiveButton("Thêm", (dialog, id) -> {
                    String name = addName.getText().toString();
                    String description = addDescription.getText().toString();
                    arrayTraiCay.add(new TraiCay(name, description, selectedImageUri));
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showFruitDialog(int index) {
        TraiCay selectedFruit = arrayTraiCay.get(index);
        selectedFruitPosition = index;

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_fruit_ask, null);
        builder.setView(dialogView);

        final EditText addName = dialogView.findViewById(R.id.addTen);
        final EditText addDescription = dialogView.findViewById(R.id.addMota);
        final Button btnAddImage = dialogView.findViewById(R.id.btnSelectImage);
        selectedImageView = dialogView.findViewById(R.id.selectedImageView);

        addName.setText(selectedFruit.getTen());
        addDescription.setText(selectedFruit.getMota());

        Glide.with(this)
                .load(selectedFruit.getHinh())
                .into(selectedImageView);

        btnAddImage.setOnClickListener(v -> {
            openImagePicker();
        });

        builder.setTitle("Chỉnh sửa trái cây")
                .setPositiveButton("Cập nhật", (dialog, id) -> {
                    String updatedName = addName.getText().toString();
                    String updatedDescription = addDescription.getText().toString();

                    selectedFruit.setTen(updatedName);
                    selectedFruit.setMota(updatedDescription);
                    if (selectedImageUri != null) {
                        selectedFruit.setHinh(selectedImageUri);
                    }

                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Xóa", (dialog, id) -> {
                    DeleteConfirm(selectedFruitPosition);
                })
                .setNeutralButton("Hủy", (dialog, id) -> dialog.cancel());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void DeleteConfirm(int index) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa trái cây này không?")
                .setPositiveButton("Xóa", (dialog, id) -> {
                    arrayTraiCay.remove(index);
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", (dialog, id) -> dialog.dismiss());

        builder.create().show();
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null && selectedImageView != null) {
                        selectedImageView.setImageURI(selectedImageUri);
                    }
                }
            }
    );
}