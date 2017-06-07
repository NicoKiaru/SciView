package sc.fiji.threed.tests.examples;

import cleargl.GLVector;
import graphics.scenery.*;
import graphics.scenery.backends.Renderer;
import graphics.scenery.repl.REPL;
import org.junit.Test;


/**
 * Created by kharrington on 7/6/16.
 *
 * This is a copy of the TextureCubeJavaExample from graphics/scenery for the purpose of sanity checking
 */
public class TexturedCubeExample {
    @Test
    public void testExample() throws Exception {
        TexturedCubeJavaApplication viewer = new TexturedCubeJavaApplication( "scenery - TexturedCubeExample", 800, 600 );
        viewer.main();
    }

    private class TexturedCubeJavaApplication extends SceneryDefaultApplication {
        public TexturedCubeJavaApplication(String applicationName, int windowWidth, int windowHeight) {
            super(applicationName, windowWidth, windowHeight, false);
        }

        public void init() {

            setRenderer( Renderer.Factory.createRenderer(getHub(), getApplicationName(), getScene(), 512, 512));
            getHub().add(SceneryElement.RENDERER, getRenderer());

            Material boxmaterial = new Material();
            boxmaterial.setAmbient( new GLVector(1.0f, 0.0f, 0.0f) );
            boxmaterial.setDiffuse( new GLVector(0.0f, 1.0f, 0.0f) );
            boxmaterial.setSpecular( new GLVector(1.0f, 1.0f, 1.0f) );
            boxmaterial.getTextures().put("diffuse", TexturedCubeJavaApplication.class.getResource("textures/spiral.png").getFile() );

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
            cam.setPosition( new GLVector(0.0f, 0.0f, 5.0f) );
            cam.perspectiveCamera(50.0f, getRenderer().getWindow().getWidth(), getRenderer().getWindow().getHeight(), 0.1f, 1000.0f);
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

            //setRepl(new REPL(getScene(), getRenderer()));
            //getRepl().start();
            //getRepl().showConsoleWindow();

        }


    }

}
