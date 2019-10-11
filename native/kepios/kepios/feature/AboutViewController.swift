//
//  AboutViewController.swift
//  kepios
//
//  Created by Romain Bsl on 09/10/2019.
//  Copyright © 2019 Romain Bsl. All rights reserved.
//
import UIKit
import MapKit
import kep_common

class AboutViewController: UIViewController, AboutInfoView {
    
    @IBOutlet weak var about_info: UILabel!
    
    private var websiteUri: String = ""
    private var twitterUri: String = ""
    private var place: Location! = nil
    
    private var presenter: AboutInfoPresenter!
    
    override func viewDidLoad() {
           super.viewDidLoad()
           initPresenter()
       }
   
   override func viewWillAppear(_ animated: Bool) {
       super.viewWillAppear(animated)
       presenter.attachView(view: self)
   }
   
   override func viewWillDisappear(_ animated: Bool) {
       super.viewWillDisappear(animated)
       presenter.detachView()
   }
    
    @IBAction func onWebsiteClick(_ sender: Any) {
        let url = URL.init(string: websiteUri)
        if #available(iOS 10, *) {
          UIApplication.shared.open(url!, options: [:])
        } else {
          UIApplication.shared.openURL(url!)
        }
    }
    
    @IBAction func onTwitterClick(_ sender: Any) {
        let url = URL.init(string: twitterUri)
        if #available(iOS 10, *) {
          UIApplication.shared.open(url!, options: [:])
        } else {
          UIApplication.shared.openURL(url!)
        }
    }
    
    @IBAction func onLocationClick(_ sender: Any) {
        let location = CLLocation(latitude: place.latitude, longitude: place.longitude)
        let mapItem = MKMapItem(
            placemark: MKPlacemark(coordinate: location.coordinate, addressDictionary: nil)
        )
        mapItem.name = place.name
        mapItem.openInMaps(launchOptions: nil)
    }
    
    private func initPresenter() {
    presenter = InjectorCommon.init().provideAboutPresenter()
}
    
    func onSuccessGetAboutInfo(about: About) {
        about_info?.text = about.info
        websiteUri = about.website
        twitterUri = about.twitter
        place = about.location
    }
    
    func onFailureGetAboutInfo(e: KotlinException) {
        //print(e.message)
    }
    
    
}
