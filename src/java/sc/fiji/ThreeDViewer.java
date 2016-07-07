package sc.fiji;

import cleargl.*;

import org.scijava.plugin.Plugin;
import org.scijava.service.Service;

import com.jogamp.opengl.GLAutoDrawable;
import scenery.*;
import scenery.rendermodules.opengl.DeferredLightingRenderer;

public class ThreeDViewer extends SceneryDefaultApplication {
	
	static ThreeDViewer viewer;
	
    public ThreeDViewer(String applicationName, int windowWidth, int windowHeight) {
        super(applicationName, windowWidth, windowHeight);
    }

    public void init(GLAutoDrawable pDrawable) {

        setDeferredRenderer( new DeferredLightingRenderer( pDrawable.getGL().getGL4(), getGlWindow().getWidth(), getGlWindow().getHeight() ) );
        getHub().add(SceneryElement.RENDERER, getDeferredRenderer());

        Material boxmaterial = new Material();
        boxmaterial.setAmbient( new GLVector(1.0f, 0.0f, 0.0f) );
        boxmaterial.setDiffuse( new GLVector(0.0f, 1.0f, 0.0f) );
        boxmaterial.setSpecular( new GLVector(1.0f, 1.0f, 1.0f) );
        //boxmaterial.getTextures().put("diffuse", SceneViewer3D.class.getResource("textures/helix.png").getFile() );

        final Box box = new Box(new GLVector(1.0f, 1.0f, 1.0f) );
        box.setMaterial( boxmaterial );
        box.setPosition( new GLVector(0.0f, 0.0f, 0.0f) );

        getScene().addChild(box);

        PointLight[] lights = new PointLight[2];

        for( int i = 0; i < lights.length; i++ ) {
            lights[i] = new PointLight();
            lights[i].setPosition( new GLVector(2.0f * i, 2.0f * i, 2.0f * i) );
            lights[i].setEmissionColor( new GLVector(1.0f, 0.0f, 1.0f) );
            lights[i].setIntensity( 0.2f*(i+1) );
            getScene().addChild( lights[i] );
        }

        Camera cam = new DetachedHeadCamera();
        cam.setPosition( new GLVector(0.0f, 0.0f, -5.0f) );
        cam.setView( new GLMatrix().setCamera(cam.getPosition(), cam.getPosition().plus(cam.getForward()), cam.getUp()) );
        cam.setProjection( new GLMatrix().setPerspectiveProjectionMatrix( (float) (70.0f / 180.0f * java.lang.Math.PI), 1024f / 1024f, 0.1f, 1000.0f) );
        cam.setActive( true );
        getScene().addChild(cam);


        Thread rotator = new Thread(){
            public void run() {
                while (true) {
                    box.getRotation().rotateByAngleY(0.01f);
                    box.setNeedsUpdate(true);

                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        rotator.start();

        getDeferredRenderer().initializeScene(getScene());

        getRepl().addAccessibleObject(getScene());
        getRepl().addAccessibleObject(getDeferredRenderer());
        //getRepl().showConsoleWindow();

        viewer = this;
    }

    public static void addSTL( String filename ) {    	
    	Mesh scMesh = new Mesh();
    	scMesh.readFromSTL( filename );
    	
    	viewer.getScene().addChild( scMesh );
    }
    
	public static void main(String... args)
	{		
		ThreeDViewer viewer = new ThreeDViewer( "ThreeDViewer", 800, 600 );
        viewer.main();
	}

}
