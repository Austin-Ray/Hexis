package io.ray.hexis.presenter

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v4.view.ViewPager
import io.ray.hexis.model.MatrixModel
import io.ray.hexis.model.QuadrantItem
import io.ray.hexis.model.abs.IMatrixModel
import io.ray.hexis.presenter.abs.IMatrixPresenter
import io.ray.hexis.util.SqlLiteHelper
import io.ray.hexis.view.MatrixFragment
import io.ray.hexis.view.abs.IMatrixFragment
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class MatrixPresenterTest {
  var mockFragment : IMatrixFragment? = null
  var mockModel : IMatrixModel? = null
  var mockHelper : SqlLiteHelper? = null
  var mockCursor: Cursor? = null

  @Before
  fun init() {
    // Create the Mock elements to managed
    mockFragment = Mockito.mock(IMatrixFragment::class.java)
    mockModel = Mockito.mock(IMatrixModel::class.java)
    mockHelper = Mockito.mock(SqlLiteHelper::class.java)
    val mockDb = Mockito.mock(SQLiteDatabase::class.java)

    // Mock the necessary Fragment function calls
    Mockito.`when`(mockFragment?.pager).thenReturn(Mockito.mock(ViewPager::class.java))

    // Mock the necessary DB calls
    Mockito.`when`(mockDb.insert(Mockito.any(), Mockito.any(),
        Mockito.any(ContentValues::class.java))).thenReturn(1L)

    mockCursor = Mockito.mock(Cursor::class.java)
    Mockito.`when`(mockCursor?.count).thenReturn(1)
    Mockito.`when`(mockDb.rawQuery(Mockito.any(), Mockito.any())).thenReturn(mockCursor)

    // Mock the necessary SqlLiteHelper function calls
    Mockito.`when`(mockHelper?.readableDatabase).thenReturn(mockDb)
  }
  @Test
  fun getPager() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, mockModel!!, mockHelper!!)
    assertNotNull(presenter.pager)
  }

  @Test
  fun addItem() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, MatrixModel(), mockHelper!!)
    presenter.addItem(0, QuadrantItem("TEST"))

    assertEquals(1, presenter.getQuadrantData(0).data.size)
  }

  @Test
  fun getFragment() {
    val presenter: IMatrixPresenter = MatrixPresenter(MatrixFragment.newInstance(), mockModel!!,
        mockHelper!!)

    assertNotNull(presenter.fragment)
  }

  @Test
  fun getModel() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, MatrixModel(), mockHelper!!)
    assertNotNull(presenter.model)
  }

  @Test
  fun setQuadrantData() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, MatrixModel(), mockHelper!!)
    val list: MutableList<QuadrantItem> = ArrayList()
    list.add(QuadrantItem("TEST"))

    assertEquals(0, presenter.getQuadrantData(0).data.size)

    presenter.setQuadrantData(0, list)
    assertEquals(1, presenter.getQuadrantData(0).data.size)
  }

  @Test
  fun notifyItemRemoved() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, MatrixModel(), mockHelper!!)
    presenter.addItem(0, QuadrantItem("TEST"))
    presenter.notifyItemRemoved(QuadrantItem("TEST"))
  }

  @Test
  fun notifyItemModified() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, MatrixModel(), mockHelper!!)
    presenter.notifyItemModified(QuadrantItem("TEST"), 0)
  }

  @Test
  fun getQuadrantData() {
    val presenter: IMatrixPresenter = MatrixPresenter(mockFragment!!, MatrixModel(), mockHelper!!)

    assertEquals(0, presenter.getQuadrantData(0).data.size)

    presenter.addItem(0, QuadrantItem("TEST"))
    assertEquals(1, presenter.getQuadrantData(0).data.size)

    assertEquals(0, presenter.getQuadrantData(1).data.size)
    assertEquals(0, presenter.getQuadrantData(2).data.size)
    assertEquals(0, presenter.getQuadrantData(3).data.size)
  }
}