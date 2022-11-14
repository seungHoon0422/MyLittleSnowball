/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Snowman_04(props) {
  const { nodes, materials } = useGLTF('/Snowman/Snowman_04.glb')
  return (
    <group {...props} dispose={null}>
      <mesh geometry={nodes.Snowman_5.geometry} material={materials.Mat} />
    </group>
  )
}

useGLTF.preload('Snowman//Snowman_04.glb')
export default Snowman_04