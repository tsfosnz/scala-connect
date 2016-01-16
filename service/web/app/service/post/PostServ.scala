package service.post

import java.text.SimpleDateFormat

import models._
import slick.driver.MySQLDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * PostServ
 *
 * @author Tom
 */

object PostServ {

  val post = PostQuery
  val topic = TopicQuery
  val topicItem = TopicItemQuery
  val db = post.db

  /**
   * Get a list of data (post, category) by (a list of category),
   * usage, when we display a list of category and for each of 
   * them, we will show a group of post in fix size
   *
   * @param topicResult
   * @param postCount
   * @param page
   * @param count
   * @return
   */
  def getAllBy(topicResult: Future[scala.Seq[TopicEntity]],
               postCount: Int,
               page: Int,
               count: Int) = {

    // To explain: 
    //
    // SELECT * FROM category LIMIT 10
    // SELECT * FROM post INNER JOIN category_item ON .. WHERE category_id = 1 LIMIT 5 
    // UNION ALL
    // SELECT * FROM post INNER JOIN category_item ON .. WHERE category_id = 2 LIMIT 5
    // ...
    // SELECT * FROM post INNER JOIN category_item ON .. WHERE category_id = 10 LIMIT 5

    try {

      val topicQuery = topic.query
      val topicItemQuery = topicItem.query

      // #question
      //
      // how post.query to support for expression?
      // SELECT * FROM post, topic_item, topic WHERE ....
      //
      val postQuery = for {

        p <- post.query
        i <- topicItemQuery if p.id === i.itemId && i.itemType === "post"
        c <- topicQuery if i.topicId === c.id

      } yield (p, c.name, c.id)


      val st = new scala.collection.mutable.Stack[Query[(Post, Rep[String], Rep[Int]), (PostEntity, String, Int), scala.Seq]]

      for {

        item <- topicResult

      } yield {

        item.map {

          c => item.size - item.indexOf(c) == 1 match {

            case true => st.push(postQuery.filter(_._3 === c.id).take(postCount * 2))
            case _ => st.push(postQuery.filter(_._3 === c.id).take(postCount))

          }
        }

        val k = st.pop
        val unionQuery = st.foldRight(k)((a, b) => a ++ b)

        val q = unionQuery.sortBy(_._3.asc).result

        //println(unionQuery.drop(page).take(count).result)
        //println(q.statements.head)
        db.run(q)
      }

    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Get a list of data (post, category) by (category id) 
   *
   * @param topicId
   * @param page
   * @param count
   * @return
   */
  def getAllBy(topicId: Int, page: Int, count: Int) = {

    try {

      val topicQuery = topic.query
      val topicItemQuery = topicItem.query

      val postQuery = for {

        p <- post.query
        i <- topicItemQuery if p.id === i.itemId && i.itemType === "post"
        c <- topicQuery if i.topicId === c.id

      } yield (p, c.name, c.id)

      val q = postQuery.filter(_._3 === topicId).drop(page).take(count)

      println(q.result.statements.head)

      db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Get a list of post by update date / time
   *
   * @param page
   * @param count
   * @return
   */
  def getAllBy(page: Int, count: Int) = {

    try {

      val topicQuery = topic.query
      val topicItemQuery = topicItem.query

      val postQuery = for {

        p <- post.query
        i <- topicItemQuery if p.id === i.itemId && i.itemType === "post"
        c <- topicQuery if i.topicId === c.id

      } yield (p, c.name, c.id)

      val q = postQuery.drop(page).take(count).sortBy(_._1.updatedAt.desc)

      println(q.result.statements.head)

      db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

  }


  /**
   * Get one post by its id
   *
   * @param id
   * @return
   */
  def one(id: Int) = {

    try {

      val q = post.query.filter(_.id === id)

      println(q.result.statements.head)

      db.run(q.result)

    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Add the new post
   *
   * @param authorId the manager id
   * @param data the data of the new item
   * @return
   */
  def add(authorId: Int, data: Map[String, Seq[String]]) = {

    try {

      // people said, use this only if it's java 8

      val dt: java.util.Date = new java.util.Date()
      val sdf: SimpleDateFormat =
        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

      val now: String = sdf.format(dt)

      val action = DBIO.seq {

        post.query.map {
          m =>
            (
              m.authorId,
              m.title,
              m.textBody,
              m.createdAt,
              m.updatedAt
              )
        } +=
          (
            authorId,
            data("title").head,
            data("body").head,
            now,
            now
            )
      }

      println(post.query.insertStatement)

      db.run(action)
    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Update the post by id
   *
   * @param id
   * @param data
   * @return
   */
  def update(id: Int, data: Map[String, Seq[String]]) = {


    try {

      val dt: java.util.Date = new java.util.Date()
      val sdf: SimpleDateFormat =
        new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

      val now: String = sdf.format(dt)

      // should only add the value in data
      // and leave all esle alone

      val q = for {
        item <- post.query if item.id === id
      } yield (item.title, item.textBody, item.updatedAt)

      val action = q.update(data("title").head, data("body").head, now)

      println(q.updateStatement)

      db.run(action)
    }

    catch {
      case err: Exception => null
    }


  }

  /**
   * Remove the post by id
   *
   * @param id
   * @return
   */
  def remove(id: Int) = {

    // first lets define a SQL

    try {

      val q = post.query.filter(_.id === id)
      println(q.delete.statements.head)

      db.run(q.delete)
    }


    catch {
      case err: Exception => null
    }

  }

  /**
   * Get a list of post by update date / time
   *
   * @param page
   * @param count
   * @return
   */
  def test(page: Int, count: Int) = {

    try {

      val topicQuery = topic.query
      val topicItemQuery = topicItem.query

      val postQuery = for {

        p <- post.query
        i <- topicItemQuery if p.id === i.itemId && i.itemType === "post"
        c <- topicQuery if i.topicId === c.id

      } yield (p, c.name, c.id)

      val q = postQuery.drop(page).take(count).sortBy(_._1.updatedAt.desc)

      println(q.result.statements.head)

      // the result always Future...
      db.run(q.result)
    }

    catch {
      case err: Exception => null
    }

  }


}
