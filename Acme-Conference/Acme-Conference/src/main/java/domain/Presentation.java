package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Presentation extends Activity {
	
	private Double cameraReadyVersion;

	@NotNull
	public Double getCameraReadyVersion() {
		return cameraReadyVersion;
	}

	public void setCameraReadyVersion(Double cameraReadyVersion) {
		this.cameraReadyVersion = cameraReadyVersion;
	}
	
}
