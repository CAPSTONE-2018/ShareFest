using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using CapstoneProj.Models;

namespace CapstoneProj.Controllers
{
    public class BusinessViewModelsController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: BusinessViewModels
        public ActionResult Index()
        {
            return View(db.BusinessViewModels.ToList());
        }

        // GET: BusinessViewModels/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            BusinessViewModel businessViewModel = db.BusinessViewModels.Find(id);
            if (businessViewModel == null)
            {
                return HttpNotFound();
            }
            return View(businessViewModel);
        }

        // GET: BusinessViewModels/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: BusinessViewModels/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,Name,Username,Address,zip,special,Email,PhoneNumber,Password,ConfirmPassword")] BusinessViewModel businessViewModel)
        {
            if (ModelState.IsValid)
            {
                db.BusinessViewModels.Add(businessViewModel);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(businessViewModel);
        }

        // GET: BusinessViewModels/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            BusinessViewModel businessViewModel = db.BusinessViewModels.Find(id);
            if (businessViewModel == null)
            {
                return HttpNotFound();
            }
            return View(businessViewModel);
        }

        // POST: BusinessViewModels/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,Name,Username,Address,zip,special,Email,PhoneNumber,Password,ConfirmPassword")] BusinessViewModel businessViewModel)
        {
            if (ModelState.IsValid)
            {
                db.Entry(businessViewModel).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(businessViewModel);
        }

        // GET: BusinessViewModels/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            BusinessViewModel businessViewModel = db.BusinessViewModels.Find(id);
            if (businessViewModel == null)
            {
                return HttpNotFound();
            }
            return View(businessViewModel);
        }

        // POST: BusinessViewModels/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            BusinessViewModel businessViewModel = db.BusinessViewModels.Find(id);
            db.BusinessViewModels.Remove(businessViewModel);
            db.SaveChanges();
            return RedirectToAction("Index");
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
