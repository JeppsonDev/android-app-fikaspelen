package cs.umu.se.fikaspelen;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * A data holder for items in the fika activity
 *
 * @since 2021-03-22
 * @author Jesper Byström
 */
public class FikaItem implements Parcelable {

    public static final int PENDING = 0;
    public static final int FAIL = 1;
    public static final int SUCCESS = 2;

    private int fikaImage;
    private String title;
    private String description;
    private String scanId;
    private int status; //0 = pending, 1 = fail, 2 = success

    /**
     * Normal Constructor
     *
     * @param fikaImage
     * @param title
     * @param description
     * @param scanId
     */
    public FikaItem(int fikaImage, String title, String description, String scanId) {
        this.fikaImage = fikaImage;
        this.title = title;
        this.description = description;
        this.scanId = scanId;
        status = PENDING;
    }

    /**
     * Parcelable constructor
     *
     * @param in
     */
    protected FikaItem(Parcel in) {
        fikaImage = in.readInt();
        title = in.readString();
        description = in.readString();
        scanId = in.readString();
        status = in.readInt();
    }

    public static final Creator<FikaItem> CREATOR = new Creator<FikaItem>() {
        @Override
        public FikaItem createFromParcel(Parcel in) {
            return new FikaItem(in);
        }

        @Override
        public FikaItem[] newArray(int size) {
            return new FikaItem[size];
        }
    };

    /**
     * Gets the image id
     *
     * @return int
     */
    public int getImageId() {
        return fikaImage;
    }

    /**
     * Gets the title
     * @return String
     */
    public String getTitle() {
       return title;
    }

    /**
     * Gets the description
     * @return String
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the scan id
     * @return String
     */
    public String getScanId() {
        return scanId;
    }

    /**
     * Gets the status
     * @return int
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the status
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fikaImage);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(scanId);
        dest.writeInt(status);
    }
}
