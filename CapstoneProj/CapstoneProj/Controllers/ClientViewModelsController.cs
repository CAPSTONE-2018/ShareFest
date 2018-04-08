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
    public class ClientViewModelsController : Controller
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: ClientViewModels
        public ActionResult Index()
        {
            return View(db.ClientViewModels.ToList());
        }

        // GET: ClientViewModels/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            ClientViewModel clientViewModel = db.ClientViewModels.Find(id);
            if (clientViewModel == null)
            {
                return HttpNotFound();
            }
            return View(clientViewModel);
        }

        // GET: ClientViewModels/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: ClientViewModels/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "id,first_name,last_name,Username,Address,zip,Email,PhoneNumber,Password,ConfirmPassword,pay")] ClientViewModel clientViewModel)
        {
            if (ModelState.IsValid)
            {
                db.ClientViewModels.Add(clientViewModel);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            return View(clientViewModel);
        }

        // GET: ClientViewModels/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            ClientViewModel clientViewModel = db.ClientViewModels.Find(id);
            if (clientViewModel == null)
            {
                return HttpNotFound();
            }
            return View(clientViewModel);
        }

        // POST: ClientViewModels/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see http://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "id,first_name,last_name,Username,Address,zip,Email,PhoneNumber,Password,ConfirmPassword,pay")] ClientViewModel clientViewModel)
        {
            if (ModelState.IsValid)
            {
                db.Entry(clientViewModel).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            return View(clientViewModel);
        }

        // GET: ClientViewModels/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            ClientViewModel clientViewModel = db.ClientViewModels.Find(id);
            if (clientViewModel == null)
            {
                return HttpNotFound();
            }
            return View(clientViewModel);
        }

        // POST: ClientViewModels/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            ClientViewModel clientViewModel = db.ClientViewModels.Find(id);
            db.ClientViewModels.Remove(clientViewModel);
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
