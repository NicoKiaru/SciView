package sc.fiji.threed.viewing;

import org.scijava.plugin.Plugin;
import org.scijava.command.Command;

import sc.fiji.ThreeDViewer;
import scenery.BoundingBox;
import scenery.Node;

@Plugin(type = Command.class, 
		menuPath = "ThreeDViewer>View>Bounding Box")
public class BoundingBoxView  implements Command {
		
	@Override
	public void run() {
		if( ThreeDViewer.getSelectedMesh() != null ) {
			BoundingBox bb = new BoundingBox();
			bb.setNode(ThreeDViewer.getSelectedMesh());
			ThreeDViewer.addChild(bb);
		}
	}

}

