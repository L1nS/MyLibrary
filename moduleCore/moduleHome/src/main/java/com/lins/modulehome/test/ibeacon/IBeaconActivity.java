package com.lins.modulehome.test.ibeacon;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.toast.ToastUtils;
import com.lins.modulehome.R;
import com.lins.modulesystem.base.BaseActivity;
import com.orhanobut.logger.Logger;

import java.util.HashMap;

public class IBeaconActivity extends BaseActivity {

    private RecyclerView idRvContent;
    private ProductAdapter adapter;
    /**
     * 搜索BLE终端
     */
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager bluetoothManager;
    private HashMap<String, IBeaconBean> map;

    @Override
    public int initLayoutResID() {
        return R.layout.activity_ibeacon;
    }

    @Override
    public void initData() {
        // 判断手机等设备是否支持BLE，即是否可以扫描iBeacon设备
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            ToastUtils.show( "当前设备不支持蓝牙功能");
            finish();
            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.show( "当前设备不支持蓝牙BLE功能");
            finish();
            return;
        }
        // 开启蓝牙
        mBluetoothAdapter.enable();
        map = new HashMap<>();
    }

    @Override
    public void initView() {
        idRvContent = findViewById(R.id.id_rv_content);
        idRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ProductAdapter();
        idRvContent.setAdapter(adapter);

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initLoad() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(IBeaconActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else
            mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    // iBeacon设备扫描回调结果
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {

        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            final IBeaconBean bean = IBeaconProductor.fromScanData(device, rssi, scanRecord);
            if (bean != null) {
                //            final Commodity commodity = getCommodity(ibeacon);
//                if (map.get(bean.uuid) == null) {
                if (map.get(bean.name) == null) {
                    map.put(bean.name, bean);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.addData(bean);
                        }
                    });
                }else{
                    Logger.d(bean.toString());
                }
            }

        }
    };
    /**
     * 模拟根据ibeacon信息从后台取对应商品据信息
     * 这里面的uuid，major，minor 都是我这边的iBeacon设备
     * 你可以填你自己的iBeacon设备相关信息，进行iBeacon设备过滤
     *
     * @return
     */
//    private Commodity getCommodity(IBeaconBean ibeacon) {
//        Log.e("ibeacon:",ibeacon.toString());
//        if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.uuid) && 10001 == ibeacon.major// 这里是对照UUID，major,minor作为模拟唯一的识别id
//                && 64120 == ibeacon.minor) {
//            return new Commodity("1122", R.drawable.a, 288.00, "老诚一锅6-8人餐\n6-8人餐,免费wifi,美味营养,回味无穷!", ibeacon.distance);
//
//        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.uuid) && 10 == ibeacon.major
//                && 7 == ibeacon.minor) {
//            return new Commodity("4455", R.drawable.b, 258.00, "净味真烤羊腿套餐\n烤羊腿套餐,可使用包间", ibeacon.distance);
//
//        } else if ("fda50693-a4e2-4fb1-afcf-c6eb07647825".equalsIgnoreCase(ibeacon.uuid)
//                && 10111 == ibeacon.major && 7 == ibeacon.minor) {
//            return new Commodity("7788", R.drawable.c, 258.00, "新疆纸皮核桃\n【全国免费配送】新疆纸皮核桃2袋共1000g,仅55元，享价值116元（原价值每袋68元）",
//                    ibeacon.distance);
//        }
//        return null;
//    }

    @Override
    protected void onPause() {
        super.onPause();
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        adapter.clear();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mBluetoothAdapter.startLeScan(mLeScanCallback);
            }
        }
    }
}
