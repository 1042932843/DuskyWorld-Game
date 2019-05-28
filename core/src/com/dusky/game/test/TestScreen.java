package com.dusky.game.test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class TestScreen implements Screen
{

    private OrthographicCamera mCamera;
    private World mWorld;
    private Box2DDebugRenderer debugRenderer;

    @Override
    public void render(float delta)
    {
        // ��ֹһ֡����ʱ������� ���¿����߶�����
        delta = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        mWorld.step(1 / 60f, 6, 2);
        // ��ͷ�ĸ��������þ���SpriteBatch
        mCamera.update();
        // ���ñ���
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        // ����
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(mWorld, mCamera.combined);
        
    }

    @Override
    public void resize(int width, int height)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void show()
    {
        // ��ͷ��Ҫ�趨���
        mCamera = new OrthographicCamera(80, 48);
        mCamera.update();

        createDynamicBody();

        createStaticBody();

        createKinematicBody();
    }

    private void createDynamicBody()
    {
        mWorld = new World(new Vector2(0f, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();
        // First we create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic, for something like ground which doesn't
        // move we would set it to StaticBody
        bodyDef.type = BodyType.DynamicBody;
        // Set our body's starting position in the world
        bodyDef.position.set(0, 0);

        // Create our body in the world using our body definition
        Body body = mWorld.createBody(bodyDef);

        // Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(6f);

        // Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

        // Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);

        // Remember to dispose of any shapes after you're done with them!
        // BodyDef and FixtureDef don't need disposing, but shapes do.
        circle.dispose();
    }

    private void createStaticBody()
    {
        // Create our body definition
        BodyDef groundBodyDef = new BodyDef();
        // Set its world position
        groundBodyDef.position.set(new Vector2(0, -20));

        // Create a body from the defintion and add it to the world
        Body groundBody = mWorld.createBody(groundBodyDef);

        // Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view
        // port and 4 high
        // (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(80/2f, 2.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        groundBody.createFixture(groundBox, 0.0f);
        // Clean up after ourselves
        groundBox.dispose();
    }
    
    private void createKinematicBody()
    {
        // Create our body definition
        BodyDef kinematicBodyDef = new BodyDef();
        //set body to Kinematic type
        kinematicBodyDef.type = BodyType.KinematicBody;
        // Set its world position
        kinematicBodyDef.position.set(new Vector2(0, 10));
        // Create a body from the defintion and add it to the world
        Body kinematicBody = mWorld.createBody(kinematicBodyDef);

        // Create a polygon shape
        PolygonShape kinematicBox = new PolygonShape();
        // Set the polygon shape as a box which is twice the size of our view
        // port and 4 high
        // (setAsBox takes half-width and half-height as arguments)
        kinematicBox.setAsBox(2.0f, 2.0f);
        // Create a fixture from our polygon shape and add it to our ground body
        kinematicBody.createFixture(kinematicBox, 1.0f);
        //set velocity
        kinematicBody.setLinearVelocity(-0.5f, -3f);
        // Clean up after ourselves
        kinematicBox.dispose();
    }

    @Override
    public void hide()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose()
    {
    }

}
