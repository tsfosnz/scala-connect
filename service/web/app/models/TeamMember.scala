package models

import slick.driver.MySQLDriver.api._

/**
 * I have no idea, if this work or not, this is a M:M relationship
 * so team : team_member : member instead, as slick wasn't ORM, so
 * we gonna make this by query
 *
 * @param tag
 */
class TeamMember(tag: Tag) extends Table[TeamMemberEntity](tag, "team_member") {

  def id = column[Option[Int]]("team_member_id", O.PrimaryKey, O.AutoInc)

  def teamId = column[Int]("team_id")
  def memberId = column[Int]("member_id")

  def * =
    (
      id,
      teamId,
      memberId) <>
      ((TeamMemberEntity.apply _).tupled, TeamMemberEntity.unapply)
}

case class TeamMemberEntity
(
  id: Option[Int],
  teamId: Int,
  memberId: Int
  )

case class TeamFullEntity
(

  team: TeamEntity,
  member: Seq[MemberEntity]
  )

