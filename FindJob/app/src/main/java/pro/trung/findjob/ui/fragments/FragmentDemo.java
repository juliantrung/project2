package pro.trung.findjob.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pro.trung.findjob.R;
import pro.trung.findjob.TuVanActivity;

/**
 * Created by Trung on 25/03/2017.
 */

public class FragmentDemo extends Fragment {

    public interface Contact{
        public void changeTrinhDo(String trinhDo);
        public void changeNganh(String nganh);
        public void changeDiaDiem(String diaDiem);
        public void changeExp(String exp);
        public void changeSalary(String salary);
        public void changeChucVu(String chucVu);
        public void changeKyNang(String kyNang);
        public void checkContact(boolean check);
    }

    Contact contact;

    private ListView lvJob;
    private FragmentManager fragmentManager;
    private Context context;
    String strTrinhDo = "Chọn trình độ học vấn";
    String strNganhHoc = "Chọn ngành nghề bạn học";
    String strNganhThich = "Chọn ngành nghề bạn yêu thích";
    String strDiaDiem = "Hà Nội";
    String strExp = "Chưa có kinh nghiệm";
    String strSalary = "Tất cả lương";
    String strChucVu = "Nhân viên";
    String strKyNang = "Tiếng anh";
    EditText etKyNang;

    String[] trinhDoArray = {"Chọn trình độ học vấn", "Chưa tốt nghiệp phổ thông", "Phổ thông", "Cao đẳng/ Trung cấp", "Đại học", "Sau đại học"};
    String[] nganhHocArray = {"Chọn ngành nghề bạn học", "CNTT phần mềm", "CNTT phần cứng", "Kế toán kiểm toán", "Cơ khí", "Marketing"};
    String[] nganhThichArray = {"Chọn ngành nghề bạn yêu thích", "CNTT phần mềm", "CNTT phần cứng", "Kế toán/ kiểm toán", "Cơ khí", "Maketing"};
    String[] diaDiemArray = {"Chọn địa điểm muốn làm", "Hà Nội", "Hồ Chí Minh", "Đà Năng", "Hải Phòng", "Cần Thơ"};
    String[] kinhNghiemArray = {"Chọn mức kinh nghiệm của bạn", "Chưa có kinh nghiệm", " < 1 năm", "1 - 2 năm", "2 - 5 năm", "5-10 năm", " > 10 năm"};
    String[] luongArray = {"Chọn mức lương bạn muốn", "Tất cả lương", "> 3 triệu", "> 5 triệu", "> 10 triệu", "> 20 triệu"};
    String[] chucVuArray = {"Chọn chức vụ bạn muốn", "Nhân viên", "Trưởng phòng", "Giám đốc", "Sinh viên", "Thực tập"};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View fragmentDemo = inflater.inflate(R.layout.fragment_main, null);

        Spinner spTringDo = (Spinner) fragmentDemo.findViewById(R.id.spTrinhDo);
        ArrayAdapter<String> trinhDoArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, trinhDoArray);
        trinhDoArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTringDo.setAdapter(trinhDoArrayAdapter);
        spTringDo.setOnItemSelectedListener(new SpinnerTringDo());

        Spinner spNganhHoc = (Spinner) fragmentDemo.findViewById(R.id.spNganhHoc);
        ArrayAdapter<String> nganhHocArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, nganhHocArray);
        nganhHocArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNganhHoc.setAdapter(nganhHocArrayAdapter);
        spNganhHoc.setOnItemSelectedListener(new SpinnerNganhHoc());

        Spinner spNganhThich = (Spinner) fragmentDemo.findViewById(R.id.spNganhThich);
        ArrayAdapter<String> nganhThichArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, nganhThichArray);
        nganhThichArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spNganhThich.setAdapter(nganhThichArrayAdapter);
        spNganhThich.setOnItemSelectedListener(new SpinnerNganhThich());

        Spinner spDiaDiem = (Spinner) fragmentDemo.findViewById(R.id.spDiaDiem);
        ArrayAdapter<String> diaDiemArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, diaDiemArray);
        diaDiemArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDiaDiem.setAdapter(diaDiemArrayAdapter);
        spDiaDiem.setOnItemSelectedListener(new SpinnerDiaDiem());

        Spinner spKinhNghiem = (Spinner) fragmentDemo.findViewById(R.id.spKinhNghiem);
        ArrayAdapter<String> kinhNghiemArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, kinhNghiemArray);
        kinhNghiemArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spKinhNghiem.setAdapter(kinhNghiemArrayAdapter);
        spKinhNghiem.setOnItemSelectedListener(new SpinnerkinhNghiem());

        Spinner spMucLuong = (Spinner) fragmentDemo.findViewById(R.id.spMucLuong);
        ArrayAdapter<String> mucLuongArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, luongArray);
        mucLuongArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMucLuong.setAdapter(mucLuongArrayAdapter);
        spMucLuong.setOnItemSelectedListener(new SpinnerMucLuong());

        Spinner spChucVu = (Spinner) fragmentDemo.findViewById(R.id.spChucVu);
        ArrayAdapter<String> chucVuArrayAdapter = new ArrayAdapter<String>(fragmentDemo.getContext(),
                android.R.layout.simple_spinner_item, chucVuArray);
        chucVuArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spChucVu.setAdapter(chucVuArrayAdapter);
        spChucVu.setOnItemSelectedListener(new SpinnerChucVu());

        etKyNang = (EditText) fragmentDemo.findViewById(R.id.etKyNang);
        etKyNang.setText("Kỹ năng của bạn:");
        strKyNang = etKyNang.getText().toString();

        Button btTuVan = (Button) fragmentDemo.findViewById(R.id.btTuVan);
        btTuVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strKyNang = etKyNang.getText().toString();
                contact.changeChucVu(strChucVu);
                contact.changeDiaDiem(strDiaDiem);
                contact.changeExp(strExp);
                contact.changeNganh(strNganhHoc);
                contact.changeSalary(strSalary);
                contact.changeTrinhDo(strTrinhDo);
                contact.changeKyNang(strKyNang);
                contact.checkContact(true);
                Intent intent = new Intent(context, TuVanActivity.class);
                intent.putExtra("nganh_hoc", strNganhHoc);
                intent.putExtra("ky_nang", strKyNang);
                intent.putExtra("dia_diem", strDiaDiem);
                intent.putExtra("kinh_nghiem", strExp);
                intent.putExtra("trinh_do", strTrinhDo);
                intent.putExtra("luong", strSalary);

                startActivity(intent);


            }
        });



        return fragmentDemo;
    }

    public class SpinnerTringDo extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strTrinhDo = parent.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerNganhHoc extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strNganhHoc = parent.getItemAtPosition(pos).toString();


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerNganhThich extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strNganhThich = parent.getItemAtPosition(pos).toString();


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerDiaDiem extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strDiaDiem = parent.getItemAtPosition(pos).toString();


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerkinhNghiem extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strExp = parent.getItemAtPosition(pos).toString();


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerChucVu extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strChucVu = parent.getItemAtPosition(pos).toString();


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    public class SpinnerMucLuong extends Activity implements AdapterView.OnItemSelectedListener{

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            strSalary = parent.getItemAtPosition(pos).toString();


        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        contact = (Contact) getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contact = (Contact) getContext();
    }
}
