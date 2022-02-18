package cs.umu.se.fikaspelen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * A class representing a fragment for each fika item
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class FikaFragment extends Fragment {

    private FikaItem fikaItem;
    private ImageView statusImage;
    private Button scanButton;
    private FloatingActionButton floatingScanButton;
    private FoundItemListener foundItemListener;

    /**
     * Empty constructor
     */
    public FikaFragment() {
    }

    public static FikaFragment newInstance(FikaItem item) {
        FikaFragment fragment = new FikaFragment();

        Bundle args = new Bundle();
        args.putParcelable("fikaItem", item);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Scans code
     */
    private void scanCode() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Lägg telefonen mot en streckkod");
        integrator.initiateScan();
    }

    /**
     * Translate an integer status to a resource
     *
     * @param status
     * @return int
     */
    private int parseStatus(int status) {
        if(status == FikaItem.PENDING) {
            return R.drawable.pending;
        } else if(status == FikaItem.FAIL) {
            return R.drawable.fail;
        } else if(status == FikaItem.SUCCESS) {
            return R.drawable.success;
        }

        return R.drawable.pending;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        //outState.putParcelable("fikaItem", fikaItem);
    }

    /**
     * Runs if the code was scanned correctly
     */
    public void success() {
        fikaItem.setStatus(FikaItem.SUCCESS);
        statusImage.setImageResource(R.drawable.success);

        updateScanButtons();
    }

    /**
     * Runs if the code that was scanned was wrong
     */
    public void fail() {
        fikaItem.setStatus(FikaItem.FAIL);

        statusImage.setImageResource(R.drawable.fail);

        updateScanButtons();
    }

    /**
     * Returns fika item
     * @return FikaItem
     */
    public FikaItem getFikaItem() {
        return fikaItem;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if(savedInstanceState != null) {
            //fikaItem = savedInstanceState.getParcelable("fikaItem");

            updateScanButtons();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if(fikaItem == null) {
            fikaItem = getArguments().getParcelable("fikaItem");
        }

        ImageView img = (ImageView)view.findViewById(R.id.img_item);
        statusImage = (ImageView)view.findViewById(R.id.img_status);
        TextView title = (TextView)view.findViewById(R.id.txt_title);
        TextView description = (TextView)view.findViewById(R.id.txt_desc);
        scanButton = (Button)view.findViewById(R.id.btn_scan);
        floatingScanButton = (FloatingActionButton)view.findViewById(R.id.actbn_scan);

        if(scanButton != null) {
            scanButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    scanCode();
                }
            });
        }
        else if(floatingScanButton != null) {
            floatingScanButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    scanCode();
                }
            });
        }

        img.setImageResource(fikaItem.getImageId());
        title.setText(fikaItem.getTitle());
        description.setText(fikaItem.getDescription());
        statusImage.setImageResource(parseStatus(fikaItem.getStatus()));

        updateScanButtons();
    }

    private void updateScanButtons() {
        if(scanButton != null) {
            if(fikaItem.getStatus() == FikaItem.SUCCESS) {
                scanButton.setClickable(false);
                scanButton.setAlpha(0.5f);
            } else {
                scanButton.setClickable(true);
                scanButton.setAlpha(1.0f);
            }
        }

        if(floatingScanButton != null) {
            if(fikaItem.getStatus() == FikaItem.SUCCESS) {
                floatingScanButton.setClickable(false);
                floatingScanButton.setAlpha(0.5f);
            } else {
                floatingScanButton.setClickable(true);
                floatingScanButton.setAlpha(1.0f);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                         @Nullable ViewGroup container,
                         @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.page, container, false);

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        FikaActivity act = (FikaActivity)getActivity();

        if(result != null) {
            if(result.getContents() != null) {
                if(act.johane || fikaItem.getScanId().equals(result.getContents())) {
                    success();
                    if(foundItemListener != null) {
                        foundItemListener.invoke();
                    }
                }
                else {
                    fail();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void setFoundItemListener(FoundItemListener l) {
        foundItemListener = l;
    }
}
