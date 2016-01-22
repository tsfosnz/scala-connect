package service.post

import java.text.SimpleDateFormat

import models._
import slick.driver.MySQLDriver.api._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author Tom
 */

object PostServ {

  val post = PostQuery
  val topic = TopicQuery
  val topicItem = TopicItemQuery
  val member = MemberQuery

  val db = post.db

  def test = {}

  /**
   * To re-use SQL probably ok
   */
  val queryPosts = for {

    p <- post.query
    i <- topicItem.query if p.id === i.itemId && i.itemType === "post"
    t <- topic.query if i.topicId === t.id
  //m <- member.query if m.id === p.authorId

  } yield (p, t)

  /**
   * Get a list of data (post, category) by (a list of category),
   * usage, when we display a list of category and for each of 
   * them, we will show a group of post in fix size
   *
   */

  def getPostsByTopics(input: Map[String, Any]) = {

    try {

      val item = input("item").asInstanceOf[Seq[TopicEntity]]
      val postCount = input("postCount").asInstanceOf[Int]

      val st = new scala.collection.mutable.Stack[Query[(Post, Topic), (PostEntity, TopicEntity), scala.Seq]]

      item.map {

        c => item.size - item.indexOf(c) == 1 match {
          case true => st.push(queryPosts.filter(_._1.id === c.id).take(postCount * 2))
          case _ => st.push(queryPosts.filter(_._1.id === c.id).take(postCount))
        }
      }

      val q = st.foldRight(st.pop())((a, b) => a ++ b).sortBy(_._1.updatedAt.asc).result

      println(q.statements.head)
      db.run(q)

    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Get a list of data (post, category) by (category id)
   */
  def getPostsByTopic(input: Map[String, Any]) = {

    try {

      val topicId = input("topicId").asInstanceOf[Int]
      val page = input("page").asInstanceOf[Int]
      val count = input("count").asInstanceOf[Int]

      val q1 = queryPosts.withFilter(_._2.id === topicId).length.result
      println(q1.statements.head)

      db.run(q1).flatMap {
        total =>
          val q = queryPosts.filter(_._2.id === topicId).take(count).drop(page)
          println(q.result.statements.head)
          db.run(q.result).map {
            item => Map("total" -> total, "data" -> item)
          }
      }
    }

    catch {
      case err: Exception => null
    }

  }

  /**
   * Get a list of post by update date / time
   */
  def getPosts(page: Int, count: Int) = {

    try {

      val result = db.run(queryPosts.length.result).flatMap {
        total =>
          val q = queryPosts.sortBy(_._1.updatedAt.desc).drop(page).take(count)

          println(q.result.statements.head)

          db.run(q.result).map {
            item => Map(
              "total" -> total,
              "data" -> item,
              "page" -> page,
              "count" -> count)
          }
      }

      println(db.source.getClass.getSimpleName)
      //db.source.close()

      result

    }

    catch {
      case err: Exception =>
        println(err.getMessage)
        null
    }

  }


  /**
   * Get one post by its id
   */
  def getPost(id: Int) = {

    try {

      // you just can't use that way, why?
      val q = for {
        (p, m) <- queryPosts join member.query  on (_._1.authorId === _.id)
      } yield (p, m.username, m.icon)

      println(q.filter(_._1._1.id === id).result.statements.head)
      db.run(q.filter(_._1._1.id === id).result)

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
