package com.frostwire.alexandria;

import com.frostwire.alexandria.db.LibraryDatabase;
import com.frostwire.alexandria.db.PlaylistItemDB;

public class PlaylistItem extends Entity<PlaylistItemDB> {

    private final Playlist _playlist;

    private int _id;
    private String _filePath;
    private String _fileName;
    private long _fileSize;
    private String _fileExtension;
    private String _trackTitle;
    private float trackDurationInSecs;
    private String _artistName;
    private String _albumName;
    private String _coverArtPath;
    private String bitrate;
    private String comment;
    private String genre;
    private String track;
    private String year;

    public PlaylistItem(Playlist playlist) {
        super(new PlaylistItemDB(playlist != null ? playlist.db.getDatabase() : null));
        _playlist = playlist;
        _id = LibraryDatabase.OBJECT_INVALID_ID;
    }

    public PlaylistItem(Playlist playlist, int id, String filePath, String fileName, long fileSize, String fileExtension, String trackTitle, float trackDurationInSecs,
            String artistName, String albumName, String coverArtPath, String bitrate, String comment,
            String genre, String track, String year) {
        super(new PlaylistItemDB(playlist.db.getDatabase()));
        _playlist = playlist;
        _id = id;
        _filePath = filePath;
        _fileName = fileName;
        _fileSize = fileSize;
        _fileExtension = fileExtension;
        _trackTitle = trackTitle;
        this.trackDurationInSecs = trackDurationInSecs;
        _artistName = artistName;
        _albumName = albumName;
        _coverArtPath = coverArtPath;
        this.bitrate = bitrate;
        this.comment = comment;
        this.genre = genre;
        this.track = track;
        this.year = year;
    }

    public Playlist getPlaylist() {
        return _playlist;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getFilePath() {
        return _filePath;
    }

    public void setFilePath(String filePath) {
        _filePath = filePath;
    }

    public String getFileName() {
        return _fileName;
    }

    public void setFileName(String fileName) {
        _fileName = fileName;
    }

    public long getFileSize() {
        return _fileSize;
    }

    public void setFileSize(long fileSize) {
        _fileSize = fileSize;
    }

    public String getFileExtension() {
        return _fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        _fileExtension = fileExtension;
    }

    public String getTrackTitle() {
        return _trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        _trackTitle = trackTitle;
    }

    public String getTrackDurationInHHMMSS() {
    	int s = (int) getTrackDurationInSecs();
    	
    	if (s < 0) {
    		s = 0;
    	}
    	
    	StringBuilder result = new StringBuilder();
    	
    	String HH = "";
    	String MM = "";
    	String SS = "";

    	//math
    	int hours=s/3600;
    	int r = s%3600;
    	int minutes = r/60;
    	int seconds = r%60;

    	//padding
    	HH = (hours < 10) ? "0"+hours : String.valueOf(hours);
    	MM = (minutes < 10) ? "0"+minutes : String.valueOf(minutes);
    	SS = (seconds < 10) ? "0"+seconds : String.valueOf(seconds);
    	
    	//lazy formatting
    	if (hours > 0) {
    		result.append(HH);
    		result.append(":");
    	}

    	result.append(MM);
    	result.append(":");
    	result.append(SS);
    	
    	return result.toString();    	
    }
    
    public float getTrackDurationInSecs() {
        return trackDurationInSecs;
    }

    public void setTrackDurationInSecs(float trackDurationInSecs) {
        this.trackDurationInSecs = trackDurationInSecs;
    }

    public String getArtistName() {
        return _artistName;
    }

    public void setArtistName(String artistName) {
        _artistName = artistName;
    }

    public String getAlbumName() {
        return _albumName;
    }

    public void setAlbumName(String albumName) {
        _albumName = albumName;
    }

    public String getCoverArtPath() {
        return _coverArtPath;
    }

    public void setCoverArtPath(String coverArtPath) {
        _coverArtPath = coverArtPath;
    }
    
    public String getBitrate() {
        return bitrate;
    }
    
    public void setBitrate(String bitrate) {
        this.bitrate = bitrate;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getTrack() {
        return track;
    }
    
    public void setTrack(String track) {
        this.track = track;
    }
    
    public String getYear() {
        return year;
    }
    
    public void setYear(String year) {
        this.year = year;
    }

    public void save() {
        if (db != null) {
            db.save(this);
        }
    }

    public void delete() {
        if (db != null) {
            db.delete(this);
        }
    }
    
    public void deleteFromAll() {
        if (db != null) {
            db.deleteFromAll(this);
        }
    }
}