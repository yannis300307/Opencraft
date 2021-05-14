package fr.opencraft.client.render.renderer.entity

import fr.opencraft.core.entity.Entities
import fr.opencraft.core.math.Euler
import fr.opencraft.core.world.EntityState
import org.lwjgl.opengl.GL11.*

class PlayerEntityRenderer : EntityRenderer(Entities.PLAYER) {
	override fun render(state: EntityState) {
		glDisable(GL_CULL_FACE)
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)
		glBegin(GL_QUADS)

		val min = state.bounds.pos
		val max = state.bounds.pos + state.bounds.size

		glColor3f(1f, 0f, 1f)
		glVertex3f(min.x, min.y, min.z)
		glVertex3f(max.x, min.y, min.z)
		glVertex3f(max.x, min.y, max.z)
		glVertex3f(min.x, min.y, max.z)

		glVertex3f(min.x, max.y, min.z)
		glVertex3f(max.x, max.y, min.z)
		glVertex3f(max.x, max.y, max.z)
		glVertex3f(min.x, max.y, max.z)

		glVertex3f(min.x, min.y, min.z)
		glVertex3f(max.x, min.y, min.z)
		glVertex3f(max.x, max.y, min.z)
		glVertex3f(min.x, max.y, min.z)

		glVertex3f(min.x, min.y, max.z)
		glVertex3f(max.x, min.y, max.z)
		glVertex3f(max.x, max.y, max.z)
		glVertex3f(min.x, max.y, max.z)
		glEnd()
		glPolygonMode(GL_FRONT_AND_BACK, GL_FILL)
		glEnable(GL_CULL_FACE)

		val head = state.head
		val look = state.head + Euler.getForward(state.rotation)

		glBegin(GL_LINES)
		glVertex3f(head.x, head.y, head.z)
		glVertex3f(look.x, look.y, look.z)
		glEnd()
	}
}