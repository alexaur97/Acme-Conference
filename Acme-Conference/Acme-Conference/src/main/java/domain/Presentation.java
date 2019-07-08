package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends Activity {
	
	private Integer cameraReadyVersion;

	@NotNull
	public Integer getCameraReadyVersion() {
		return cameraReadyVersion;
	}

	public void setCameraReadyVersion(Integer cameraReadyVersion) {
		this.cameraReadyVersion = cameraReadyVersion;
	}
	
}
