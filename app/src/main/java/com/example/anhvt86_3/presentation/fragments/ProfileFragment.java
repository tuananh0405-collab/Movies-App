package com.example.anhvt86_3.presentation.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.example.anhvt86_3.databinding.FragmentProfileBinding;

import java.io.IOException;
import java.util.Calendar;

import javax.inject.Inject;

public class ProfileFragment extends Fragment {
//    @Inject
//    ProfileViewModel viewModel;
//    private ActivityResultLauncher<Intent> cameraLauncher;
//    private ActivityResultLauncher<Intent> galleryLauncher;
    private FragmentProfileBinding binding;

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == Activity.RESULT_OK) {
//                Intent data = result.getData();
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap imageBitmap = (Bitmap) extras.get("data");
//                    updateProfileImage(imageBitmap);
//                }
//            }
//        });
//
//        galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//            if (result.getResultCode() == Activity.RESULT_OK) {
//                Intent data = result.getData();
//                if (data != null) {
//                    Uri imageUri = data.getData();
//                    try {
//                        Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
//                        updateProfileImage(imageBitmap);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ((MainActivity) requireActivity()).getAppComponent().inject(this);
//
//        binding.setUserProfile(viewModel.getUserProfileLiveData().getValue());
//        Log.e("Gender", binding.getUserProfile().getGender());
//        binding.cameraBtn.setOnClickListener(v -> {
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, 1);
//                return;
//            }
//            cameraLauncher.launch(cameraIntent);
//        });
//
//        binding.galleryBtn.setOnClickListener(v -> {
//            Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//            galleryLauncher.launch(pickPhoto);
//        });
//
//
////        viewModel.getUserProfileLiveData().observe(getViewLifecycleOwner(), userProfile -> {
////            binding.setUserProfile(userProfile);
//
//            if ("male".equals(binding.getUserProfile().getGender())) {
//                Log.e("Gender", "male");
//                binding.rbtnMale.setChecked(true);
//            } else if ("female".equals(binding.getUserProfile().getGender())) {
//                binding.rbtnFemale.setChecked(true);
//            }
////        });
//
//
//        binding.doneButton.setOnClickListener(v -> {
//            String gender = binding.radioGroup.getCheckedRadioButtonId() == R.id.rbtnMale ? "male" : "female";
//
//
//            UserProfile userProfile = new UserProfile(
//                    binding.fullNameEditText.getText().toString(),
//                    binding.emailEditText.getText().toString(),
//                    binding.birthdayEditText.getText().toString(),
//                    binding.getUserProfile() != null ? binding.getUserProfile().getProfileImage() : null,
//                    gender // Gán giá trị gender lấy từ RadioGroup
//            );
//
//            Log.e("AVT", binding.getUserProfile().getProfileImage());
//            viewModel.saveUserProfile(userProfile);
//            Navigation.findNavController(requireView()).navigateUp();
//        });
//
//
//        binding.cancelButton.setOnClickListener(v -> {
//            Navigation.findNavController(requireView()).navigateUp();
//        });
//        binding.birthdayButton.setOnClickListener(view1 -> {
//            Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view2, selectedYear, selectedMonth, selectedDay) -> {
//                String selectedDate = selectedYear + "/" + (selectedMonth + 1) + "/" + selectedDay;
//                binding.birthdayEditText.setText(selectedDate);
//            }, year, month, day);
//            datePickerDialog.show();
//        });
//        viewModel.getUserProfileLiveData().observe(getViewLifecycleOwner(), userProfile -> {
//            binding.setUserProfile(userProfile);
//        });
//    }
//
//    private void updateProfileImage(Bitmap bitmap) {
//        String base64Image = viewModel.convertBitmapToBase64(bitmap);
//        UserProfile userProfile = binding.getUserProfile();
//        if (userProfile != null) {
//            userProfile.setProfileImage(base64Image);
//            binding.setUserProfile(userProfile);
//            binding.displayImageView.setImageBitmap(bitmap);
//        }
//    }
}