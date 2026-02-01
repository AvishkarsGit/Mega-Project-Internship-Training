package com.avishkar.megaproject.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.avishkar.megaproject.R;
import com.avishkar.megaproject.databinding.ActivityAddProductBinding;
import com.avishkar.megaproject.helper.DatabaseHelper;
import com.avishkar.megaproject.models.ProductsModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddProductActivity extends AppCompatActivity {

    private ActivityAddProductBinding binding;
    private String path;
    private DatabaseHelper helper;
    private String productTitle, productDescription, productPrice, productDiscount;
    private boolean isDiscount,isTax;
    private int discount_percent=0;

    //model
    private ProductsModel model;

    //is Edited
    private boolean isEdited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialize
        init();

        //switch compat
        binding.discountSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isDiscount = true;
                    //show edittext
                    binding.edtDiscount.setVisibility(View.VISIBLE);
                }
                else {
                    isDiscount = false;
                    binding.edtDiscount.setVisibility(View.GONE);
                }
            }
        });

        //checkbox
        binding.taxCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                isTax = isChecked;
            }
        });

        //click on choose image
        binding.productIv.setOnClickListener(v-> {
            chooseImage();
        });

        //handle click on back button
        binding.backIb.setOnClickListener(v -> {
            finish();
        });

        //handle click on save button
        binding.btnSaveProduct.setOnClickListener(v -> {
            if (validate()) {
                if (isEdited) {
                    //edit data
                    boolean isUpdated = helper.updateProduct(
                            model.getId(),
                            productTitle,
                            productDescription,
                            path,
                            productPrice,
                            isDiscount,
                            discount_percent,
                            isTax
                    );
                    if (isUpdated) {
                        Toast.makeText(this, "Product Updated", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    boolean isAdded = helper.addProduct(
                            productTitle,
                            productDescription,
                            path,
                            productPrice,
                            isDiscount,
                            discount_percent,
                            isTax
                    );
                    if (isAdded) {
                        Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

        //set all the data
        if (isEdited) {
            setData();
        }

    }

    private void setData() {
        binding.toolbarTitle.setText("Edit Product");

        //set all data
        productTitle = model.getProductTitle();
        productDescription = model.getProductDescription();
        productPrice = model.getProductPrice();
        path = model.getProductImage();
        isDiscount = model.isDiscount();
        discount_percent = model.getProductDiscount();
        isTax = model.isTax();

        binding.edtTitle.setText(productTitle);
        binding.edtDescription.setText(productDescription);
        binding.edtPrice.setText(productPrice);

        //if discount is available
        binding.discountSwitch.setChecked(isDiscount);
        binding.edtDiscount.setText(""+discount_percent);
        binding.taxCheckbox.setChecked(isTax);



        try {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            binding.productIv.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //change button text
        binding.btnSaveProduct.setText("Update Product");
    }

    private void init(){
        model = (ProductsModel) getIntent().getSerializableExtra("product");
        isEdited = (Boolean) getIntent().getBooleanExtra("isEdited",false);
        helper = new DatabaseHelper(AddProductActivity.this);
    }

    private void chooseImage() {
        // Launch the photo picker and let the user choose only images.
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE)
                .build());
    }

    private boolean validate() {
        productTitle = binding.edtTitle.getText().toString().trim();
        productDescription = binding.edtDescription.getText().toString().trim();
        productPrice = binding.edtPrice.getText().toString().trim();

        if (productTitle.isEmpty()) {
            Toast.makeText(this, "Enter product title", Toast.LENGTH_SHORT).show();
            return false;
        } else if (productDescription.isEmpty()) {
            Toast.makeText(this, "Enter product description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (productPrice.isEmpty()) {
            Toast.makeText(this, "Enter product price", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(Integer.parseInt(productPrice) < 1) {
            Toast.makeText(this, "Price should be greater than 0", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (path == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (isDiscount) {
            productDiscount = binding.edtDiscount.getText().toString().trim();
            if (productDiscount.isEmpty()) {
                Toast.makeText(this, "Enter discount", Toast.LENGTH_SHORT).show();
                return false;
            }
            else  if (Integer.parseInt(productDiscount) < 1) {
                Toast.makeText(this, "Enter non-zero value", Toast.LENGTH_SHORT).show();
                return false;
            }
            else if (Integer.parseInt(productDiscount) > 20) {
                Toast.makeText(this, "Maximum discount allowed only 20%", Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
                discount_percent = Integer.parseInt(productDiscount);
                return true;
            }
        }
        else {
            return true;
        }
    }

    private String copyImageToInternalStorage(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);

        File file = new File(
                getFilesDir(),
                "img_" + System.currentTimeMillis() + ".jpg"
        );

        FileOutputStream outputStream = new FileOutputStream(file);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }

        inputStream.close();
        outputStream.close();

        return file.getAbsolutePath();
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    try {
                        binding.productIv.setImageURI(uri);
                        path = copyImageToInternalStorage(uri);

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "No toast selected", Toast.LENGTH_SHORT).show();
                }
            });
}