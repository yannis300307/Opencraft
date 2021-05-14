#version 120

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

varying vec4 varColor;

void main() {
    gl_Position = projectionMatrix * viewMatrix * gl_Vertex;
    varColor = gl_Color;
}