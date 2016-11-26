package com.edu.utn.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;

import com.edu.utn.infoba.modelo.PuntoDeInteres;
import org.hibernate.Transaction;

public class PoisMySQL implements DataBase
{

	private EntityManager em;
	
	public PoisMySQL(EntityManager entityManager){
		em = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PuntoDeInteres> buscarPuntoDeInteresPorTexto(String texto)
    {
		//List<PuntoDeInteres> poisEncontrados = null;
		List<PuntoDeInteres> pois = new ArrayList<>();
		pois.addAll(em.createNamedQuery("buscarPoiPorNombre").setParameter("texto", "%" + texto + "%").getResultList());
		pois.addAll(em.createNamedQuery("buscarPoiPorRubro").setParameter("texto", "%" + texto + "%").getResultList());
		pois.addAll(em.createNamedQuery("buscarPoiPorServicio").setParameter("texto", "%" + texto + "%").getResultList());
		pois.addAll(em.createNamedQuery("buscarPoiPorTag").setParameter("texto", "%" + texto + "%").getResultList());

		return pois;
	}

	public void borradoFisico(List<PuntoDeInteres> puntosDeInteres)
	{
		javax.persistence.EntityTransaction transaction = em.getTransaction();
		transaction.begin();

		for(PuntoDeInteres poi : puntosDeInteres)
		{
			em.remove(poi);
		}

		transaction.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PuntoDeInteres buscarPuntoDeInteresPorNombre(String texto)
	{
		List<PuntoDeInteres> pois = new ArrayList<>();

		pois.addAll(em.createNamedQuery("buscarPoiPorNombre").setParameter("texto", "%" + texto + "%").getResultList());

		if(pois.iterator().hasNext())
			return pois.iterator().next();
		else
			return null;
	}

	@Override
	public void persistirPuntoDeInteres(PuntoDeInteres puntoDeInteres) {
		em.getTransaction().begin();
		em.persist(puntoDeInteres);
		em.getTransaction().commit();
	}

	@Override
	public void eliminarPuntoDeInteres(PuntoDeInteres puntoDeInteres)
    {
        javax.persistence.EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(puntoDeInteres);
        transaction.commit();
	}

    @Override
    public void bajaPuntoDeInteres(PuntoDeInteres puntoDeInteres)
    {
        javax.persistence.EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        puntoDeInteres.darDeBaja();
        em.merge(puntoDeInteres);
        transaction.commit();
    }


    public void modificarPuntoDeInteres(PuntoDeInteres puntoDeInteres)
    {
		em.getTransaction().begin();
		em.merge(puntoDeInteres);
		em.getTransaction().commit();
	}

}
