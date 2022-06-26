
package com.kostasdrakonakis.flappybird

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import java.util.*

class FlappyBird : ApplicationAdapter() {

    private lateinit var batch: SpriteBatch
    private lateinit var background: Texture
    private lateinit var gameOver: Texture
    private lateinit var birds: Array<Texture>
    private lateinit var topTubeRectangles: Array<Rectangle?>
    private lateinit var bottomTubeRectangles: Array<Rectangle?>
    private lateinit var birdCircle: Circle
    private lateinit var font: BitmapFont
    private lateinit var topTube: Texture
    private lateinit var bottomTube: Texture
    private lateinit var random: Random

    private var flapState = 0
    private var birdY: Float = 0f
    private var velocity: Float = 0f
    private var score: Int = 0
    private var scoringTube: Int = 0
    private var gameState: Int = 0
    private val numberOfTubes: Int = 5
    private var gdxHeight: Int = 0
    private var gdxWidth: Int = 0
    private var topTubeWidth: Int = 0
    private var bottomTubeWidth: Int = 0
    private var topTubeHeight: Int = 0
    private var bottomTubeHeight: Int = 0

    private val tubeX = FloatArray(numberOfTubes)
    private val tubeOffset = FloatArray(numberOfTubes)
    private var distanceBetweenTubes: Float = 0.toFloat()

    override fun create() {
        batch = SpriteBatch()
        background = Texture("suratibat.jpg")
        gameOver = Texture("gameover.png")
        birdCircle = Circle()
        font = BitmapFont()
        font.color = Color.WHITE
        font.data.setScale(10f)

        birds = arrayOf(Texture("bird.png"), Texture("bird2.png"))// ase gavaketebt efeqts titqos chiti  dafrinavs

        gdxHeight = Gdx.graphics.height
        gdxWidth = Gdx.graphics.width

        topTube = Texture("toptube.png")// zeda  dabrkoleba
        bottomTube = Texture("bottomtube.png")//qveda dabrkoleba
        random = Random()
        distanceBetweenTubes = gdxWidth * 3f / 4f
        topTubeRectangles = arrayOfNulls(numberOfTubes)// ganvsazgvrot  raodenoba svetebis
        bottomTubeRectangles = arrayOfNulls(numberOfTubes)//ganvsazgvrot svetebis raodenoba

        topTubeWidth = topTube.width
        topTubeHeight = topTube.height
        bottomTubeWidth = bottomTube.width
        bottomTubeHeight = bottomTube.height

        startGame()//daviwyet tamashi
    }

    override fun render() {
        batch.begin()
        batch.draw(background, 0f, 0f, gdxWidth.toFloat(), gdxHeight.toFloat())

        if (gameState == 1) {//tu tamashi dawytebulia

            if (tubeX[scoringTube] < gdxWidth / 2) {
                score++ //tu yvelaferi kargada  da tamashi mimdinareobs ise ro  xels  araferi gvishlis mashin qula imatebs
                if (scoringTube < numberOfTubes - 1) {
                    scoringTube++ //tu  ar  dafailda   motamashe mashin  moimatebs  scoringtube
                } else {
                    scoringTube = 0 // win agmdeg shemtxvevashi tamashi nuldeba
                }
            }

            if (Gdx.input.justTouched()) {
                velocity = -30f
            }

            for (i in 0 until numberOfTubes) {

                if (tubeX[i] < -topTubeWidth) {
                    tubeX[i] += numberOfTubes * distanceBetweenTubes
                    tubeOffset[i] = (random.nextFloat() - 0.5f) * (gdxHeight.toFloat() - GAP - 200f)
                } else {
                    tubeX[i] = tubeX[i] - TUBE_VELOCITY
                }

                batch.draw(topTube, tubeX[i], gdxHeight / 2f + GAP / 2 + tubeOffset[i])
                batch.draw(bottomTube,
                        tubeX[i],
                        gdxHeight / 2f - GAP / 2 - bottomTubeHeight.toFloat() + tubeOffset[i])

                topTubeRectangles[i] = Rectangle(tubeX[i],
                        gdxHeight / 2f + GAP / 2 + tubeOffset[i],
                        topTubeWidth.toFloat(),
                        topTubeHeight.toFloat())

                bottomTubeRectangles[i] = Rectangle(tubeX[i],
                        gdxHeight / 2f - GAP / 2 - bottomTubeHeight.toFloat() + tubeOffset[i],
                        bottomTubeWidth.toFloat(),
                        bottomTubeHeight.toFloat())
            }

            if (birdY > 0) {
                velocity += GRAVITY
                birdY -= velocity
            } else {
                gameState = 2
            }

        }
        else if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1
            }
        }
        else if (gameState == 2) {// tu tamashi wavaget
            batch.draw(gameOver, gdxWidth / 2f - gameOver.width / 2f, gdxHeight / 2f - gameOver.height / 2f)

            if (Gdx.input.justTouched()) {//tu ekranze shexo motamashe
                gameState = 1
                startGame()//tamashs viwyebt tavidan
                score = 0//qula nuldeba
                scoringTube = 0
                velocity = 0f
            }
        }

        flapState = if (flapState == 0) 1 else 0

        batch.draw(birds[flapState], gdxWidth / 2f - birds[flapState].width / 2f, birdY)
        font.draw(batch, score.toString(), 100f, 200f)
        birdCircle.set(gdxWidth / 2f,
                birdY + birds[flapState].height / 2f,
                birds[flapState].width / 2f)

        for (i in 0 until numberOfTubes) {
            if (Intersector.overlaps(birdCircle, topTubeRectangles[i])
                    || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) gameState = 2
        }

        batch.end()
    }

    private fun startGame() {
        birdY = gdxHeight / 2f - birds[0].height / 2f

        for (i in 0 until numberOfTubes) {
            tubeOffset[i] = (random.nextFloat() - 0.5f) * (gdxHeight.toFloat() - GAP - 200f)
            tubeX[i] = gdxWidth / 2f - topTubeWidth / 2f + gdxWidth.toFloat() + i * distanceBetweenTubes
            topTubeRectangles[i] = Rectangle()
            bottomTubeRectangles[i] = Rectangle()
        }
    }

    companion object {
        private const val GRAVITY = 2f
        private const val TUBE_VELOCITY = 4f
        private const val GAP = 800f
    }
}
